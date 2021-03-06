package de.javagl.jgltf.viewer;

import de.javagl.jgltf.logging.Logger;
import de.javagl.jgltf.model.CameraModel;
import de.javagl.jgltf.model.GltfAnimations;
import de.javagl.jgltf.model.GltfModel;
import de.javagl.jgltf.model.TextureModel;
import de.javagl.jgltf.model.animation.Animation;
import de.javagl.jgltf.model.animation.AnimationManager;
import de.javagl.jgltf.model.animation.AnimationManager.AnimationPolicy;
import de.javagl.jgltf.model.animation.AnimationRunner;
import de.javagl.jgltf.model.v1.GltfModelV1;
import de.javagl.jgltf.model.v2.GltfModelV2;

import java.util.*;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Abstract base implementation of a {@link GltfViewer}
 *
 * @param <C> The type of the render component of this viewer
 */
public abstract class AbstractGltfViewer<C> implements GltfViewer<C> {

    /**
     * The logger used in this class
     */
    private static final Logger logger =
            Logger.getLogger(AbstractGltfViewer.class);

    /**
     * A supplier of the viewport size. This will be passed to the
     * {@link RenderedGltfModel} constructor, and eventually provide the data for
     * the uniforms that have the <code>VIEWPORT</code> semantic.
     */
    private final Supplier<float[]> viewportSupplier = new Supplier<float[]>() {
        private final float[] viewport = new float[4];

        @Override
        public float[] get() {
            viewport[0] = 0;
            viewport[1] = 0;
            viewport[2] = getWidth();
            viewport[3] = getHeight();
            return viewport;
        }
    };

    /**
     * A supplier for the aspect ratio. This will provide the aspect ratio
     * of the rendering window. (If this was <code>null</code>,
     * then the aspect ratio of the glTF camera would be
     * used, but this would hardly ever match the actual aspect
     * ratio of the rendering component...)
     */
    public final DoubleSupplier aspectRatioSupplier = () -> ((double) getWidth()) / getHeight();

    /**
     * An optional {@link ExternalCamera}.
     * See {@link #setExternalCamera(ExternalCamera)} for details.
     */
    public ExternalCamera externalCamera;

    /**
     * Tasks that have to be executed before the next rendering pass,
     * on the rendering thread
     */
    private final List<Runnable> beforeRenderTasks;

    /**
     * The map from {@link GltfModel} instances to their
     * {@link RenderedGltfModel} counterparts
     */
    public final Map<GltfModel, RenderedGltfModel> renderedGltfModels;

    /**
     * The list of {@link GltfModel} instances that have been added.
     */
    public final List<GltfModel> gltfModels;

    /**
     * The {@link AnimationManager}
     */
    public final AnimationManager animationManager;

    /**
     * The {@link AnimationRunner}
     */
    public final AnimationRunner animationRunner;

    /**
     * The map from {@link GltfModel} instances to the lists of
     * model {@link Animation}s that have been created for the
     * glTF animations
     */
    public final Map<GltfModel, List<Animation>> modelAnimations;

    /**
     * Default constructor
     */
    protected AbstractGltfViewer() {
        this.beforeRenderTasks = Collections.synchronizedList(new ArrayList<>());
        this.renderedGltfModels = new LinkedHashMap<>();
        this.gltfModels = new ArrayList<>();
        this.animationManager = GltfAnimations.createAnimationManager(AnimationPolicy.LOOP);
        this.animationManager.addAnimationManagerListener(a -> triggerRendering());
        this.animationRunner = new AnimationRunner(animationManager);
        this.modelAnimations = new LinkedHashMap<>();
        setAnimationsRunning(true);
    }

    @Override
    public final void setExternalCamera(ExternalCamera externalCamera) {
        this.externalCamera = externalCamera;
    }

    @Override
    public final void setAnimationsRunning(boolean running) {
        if (running) {
            animationRunner.start();
        } else {
            animationRunner.stop();
        }
    }

    @Override
    public abstract C getRenderComponent();

    /**
     * Returns the {@link GlContext} of this viewer
     *
     * @return The {@link GlContext} of this viewer
     */
    protected abstract GlContext getGlContext();

    @Override
    public final void addGltfModel(GltfModel gltfModel) {
        Objects.requireNonNull(gltfModel, "The gltfModel may not be null");
        gltfModels.add(gltfModel);
        addBeforeRenderTask(() -> createRenderedGltf(gltfModel));
        triggerRendering();

        // If no external camera has been defined, set the current camera
        // to be the first camera of the given model.
        if (externalCamera == null) {
            List<CameraModel> cameraModels = gltfModel.getCameraModels();
            if (!cameraModels.isEmpty()) {
                CameraModel cameraModel = cameraModels.get(0);
                setCurrentCameraModel(gltfModel, cameraModel);
            }
        }
    }

    /**
     * Create a {@link RenderedGltfModel} for the given {@link GltfModel}, and
     * store it in the {@link #renderedGltfModels} map
     *
     * @param gltfModel The {@link GltfModel}
     */
    private void createRenderedGltf(GltfModel gltfModel) {
        Supplier<float[]> viewMatrixSupplier = null;
        Supplier<float[]> projectionMatrixSupplier = null;
        if (externalCamera != null) {
            viewMatrixSupplier = () -> externalCamera.getViewMatrix();
            projectionMatrixSupplier = () -> externalCamera.getProjectionMatrix();
        }

        logger.info("Creating rendered glTF");

        ViewConfiguration viewConfiguration =
                new ViewConfiguration(
                        viewportSupplier, aspectRatioSupplier,
                        viewMatrixSupplier, projectionMatrixSupplier);

        GlContext glContext = getGlContext();
        RenderedGltfModel renderedGltfModel;

        if (gltfModel instanceof GltfModelV1) {
            GltfModelV1 gltfModelV1 = (GltfModelV1) gltfModel;
            Function<Object, TextureModel> textureModelLookup = object -> {
                String textureId = String.valueOf(object);
                return gltfModelV1.getTextureModelById(textureId);
            };
            renderedGltfModel = new DefaultRenderedGltfModel(glContext, gltfModelV1, textureModelLookup, viewConfiguration);
        } else if (gltfModel instanceof GltfModelV2) {
            GltfModelV2 gltfModelV2 = (GltfModelV2) gltfModel;
            Function<Object, TextureModel> textureModelLookup = object -> {
                Number number = (Number) object;
                int index = number.intValue();
                return gltfModelV2.getTextureModels().get(index);
            };
            renderedGltfModel = new DefaultRenderedGltfModel(glContext, gltfModelV2, textureModelLookup, viewConfiguration);
        } else {
            logger.severe("GltfModel version is not supported: " + gltfModel);
            return;
        }

        renderedGltfModels.put(gltfModel, renderedGltfModel);

        List<Animation> currentModelAnimations =
                GltfAnimations.createModelAnimations(gltfModel.getAnimationModels());
        modelAnimations.put(gltfModel, currentModelAnimations);
        animationManager.addAnimations(currentModelAnimations);
    }

    @Override
    public void removeGltfModel(GltfModel gltfModel) {
        Objects.requireNonNull(gltfModel, "The gltfModel may not be null");
        gltfModels.remove(gltfModel);
        addBeforeRenderTask(() -> deleteRenderedGltfModel(gltfModel));
        List<Animation> currentModelAnimations = modelAnimations.get(gltfModel);
        if (currentModelAnimations != null) {
            animationManager.removeAnimations(currentModelAnimations);
        }
        modelAnimations.remove(gltfModel);
        triggerRendering();
    }

    /**
     * Delete the {@link RenderedGltfModel} that is associated with the given
     * {@link GltfModel}
     *
     * @param gltfModel The {@link GltfModel}
     */
    private void deleteRenderedGltfModel(GltfModel gltfModel) {
        RenderedGltfModel renderedGltfModel = renderedGltfModels.get(gltfModel);
        if (renderedGltfModel == null) {
            logger.warning("No renderedGltfModel found for gltfModel " + gltfModel);
            return;
        }

        logger.info("Deleting rendered glTF");

        renderedGltfModel.delete();
        renderedGltfModels.remove(gltfModel);
    }

    @Override
    public List<CameraModel> getCameraModels() {
        List<CameraModel> cameraModels = new ArrayList<>();
        for (GltfModel gltfModel : gltfModels) {
            cameraModels.addAll(gltfModel.getCameraModels());
        }
        return Collections.unmodifiableList(cameraModels);
    }

    @Override
    public void setCurrentCameraModel(
            GltfModel gltfModel, CameraModel cameraModel) {
        if (gltfModel != null && !gltfModels.contains(gltfModel)) {
            throw new IllegalArgumentException(
                    "The given gltfModel is not contained in this viewer");
        }
        addBeforeRenderTask(
                () -> setCurrentCameraModelInternal(gltfModel, cameraModel));
        triggerRendering();
    }

    /**
     * Implementation of {@link #setCurrentCameraModel(GltfModel, CameraModel)},
     * to be called before a rendering pass
     *
     * @param gltfModel   The {@link GltfModel}
     * @param cameraModel The {@link CameraModel}
     */
    private void setCurrentCameraModelInternal(
            GltfModel gltfModel, CameraModel cameraModel) {
        if (gltfModel == null) {
            for (RenderedGltfModel renderedGltf : renderedGltfModels.values()) {
                renderedGltf.setCurrentCameraModel(cameraModel);
            }
        } else {
            RenderedGltfModel renderedGltf = renderedGltfModels.get(gltfModel);
            if (renderedGltf == null) {
                logger.warning("Rendered glTF model has been removed");
                return;
            }
            renderedGltf.setCurrentCameraModel(cameraModel);
        }
    }

    /**
     * Add a task to be executed once, before the next rendering pass,
     * on the rendering thread
     *
     * @param beforeRenderTask The task to be executed
     */
    private void addBeforeRenderTask(Runnable beforeRenderTask) {
        beforeRenderTasks.add(beforeRenderTask);
    }

    /**
     * The method that may be called by implementations to execute the
     * actual rendering pass. It will call {@link #prepareRender()}
     * (then execute all tasks that have been scheduled internally,
     * for execution on the rendering thread), and then call
     * {@link #render()}.
     */
    protected final void doRender() {
        prepareRender();
        beforeRender();
        render();
    }

    /**
     * Will be called at the beginning of each rendering pass. May be
     * used to do basic setup, e.g. to make the required GL context
     * current.
     */
    protected abstract void prepareRender();

    /**
     * Will be called between {@link #prepareRender()} and {@link #render()},
     * and process all {@link #beforeRenderTasks}
     */
    private void beforeRender() {
        synchronized (beforeRenderTasks) {
            while (beforeRenderTasks.size() > 0) {
                Runnable beforeRenderTask = beforeRenderTasks.get(0);
                beforeRenderTask.run();
                beforeRenderTasks.remove(0);
            }
        }
    }

    /**
     * The actual rendering method. Subclasses implementing this method
     * will usually call {@link #renderGltfModels()}.
     */
    protected abstract void render();

    /**
     * Render all glTF models that have been added via
     * {@link #addGltfModel(GltfModel)}
     */
    protected final void renderGltfModels() {
        for (RenderedGltfModel renderedGltfModel : renderedGltfModels.values()) {
            renderedGltfModel.render();
        }
    }


}
