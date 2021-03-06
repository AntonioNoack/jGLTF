package de.javagl.jgltf.viewer;

import java.util.Objects;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import de.javagl.jgltf.model.CameraModel;
import de.javagl.jgltf.model.MathUtils;

/**
 * The view configuration for a single {@link RenderedGltfModel}. It allows
 * setting the {@link #setCurrentCameraModel(CameraModel) current camera model}
 * that should be used for rendering, and offers the viewport, view matrix
 * and projection matrix. 
 */
final class ViewConfiguration
{
    /**
     * A {@link SettableSupplier} that provides the {@link CameraModel}
     * that should be used for rendering.  
     * See {@link #setCurrentCameraModel(CameraModel)}
     */
    private final SettableSupplier<CameraModel> currentCameraModelSupplier;

    /**
     * The supplier for the viewport, as an array [x, y, width, height]
     */
    private final Supplier<float[]> viewportSupplier;
    
    /**
     * An optional supplier for the aspect ratio. If this is <code>null</code>, 
     * then the aspect ratio of the camera will be used     
     */
    private final DoubleSupplier aspectRatioSupplier;
    
    /**
     * The supplier for the view matrix
     */
    private final Supplier<float[]> viewMatrixSupplier;
    
    /**
     * The supplier for the projection matrix
     */
    private final Supplier<float[]> projectionMatrixSupplier;
    
    /**
     * Creates a new view configuration
     *  
     * @param viewportSupplier A supplier that supplies the viewport, 
     * as 4 float elements, [x, y, width, height]
     * @param aspectRatioSupplier An optional supplier for the aspect ratio. 
     * If this is <code>null</code>, then the aspect ratio of the 
     * camera will be used
     * @param externalViewMatrixSupplier The optional external supplier of
     * a view matrix.
     * @param externalProjectionMatrixSupplier The optional external supplier
     * of a projection matrix.
     */
    ViewConfiguration(
        Supplier<float[]> viewportSupplier,
        DoubleSupplier aspectRatioSupplier,
        Supplier<float[]> externalViewMatrixSupplier, 
        Supplier<float[]> externalProjectionMatrixSupplier)
    {
        this.viewportSupplier = Objects.requireNonNull(
            viewportSupplier, "The viewportSupplier may not be null");
        this.currentCameraModelSupplier = new SettableSupplier<CameraModel>();
        this.aspectRatioSupplier = aspectRatioSupplier;
        this.viewMatrixSupplier = 
            createViewMatrixSupplier(externalViewMatrixSupplier);
        this.projectionMatrixSupplier = 
            createProjectionMatrixSupplier(externalProjectionMatrixSupplier);
    }
    
    /**
     * Set the {@link CameraModel} that should be used for rendering. 
     * If the given {@link CameraModel} is <code>null</code>,
     * then the external camera will be used.<br>
     * <br>
     * @param cameraModel The {@link CameraModel} 
     */
    public void setCurrentCameraModel(CameraModel cameraModel)
    {
        currentCameraModelSupplier.set(cameraModel);
    }
    
    /**
     * Create a supplier for the view matrix, as a float array with 16 
     * elements, containing the view matrix in column-major order.<br>
     * <br>
     * The resulting supplier will supply a view matrix as follows:
     * <ul>
     *   <li> 
     *     If a non-<code>null</code> {@link #setCurrentCameraModel
     *     current camera model} has been set, then the view matrix from
     *     this {@link CameraModel} will be returned
     *   </li>
     *   <li>
     *     Otherwise, if the external matrix supplier is non-<code>null</code>,
     *     then its matrix will be returned
     *   </li>
     *   <li>
     *     Otherwise, an identity matrix will be returned
     *   </li>
     * </ul>
     * Note: The supplier MAY always return the same array instance.
     * Callers MUST NOT store or modify the returned array. 
     * 
     * @param externalViewMatrixSupplier The optional external view matrix
     * supplier that may have been given in the constructor
     * @return The view matrix supplier
     */
    private Supplier<float[]> createViewMatrixSupplier(
        Supplier<float[]> externalViewMatrixSupplier)
    {
        float[] viewMatrix = MathUtils.createIdentity4x4();
        return () ->
        {
            CameraModel cameraModel = currentCameraModelSupplier.get();
            if (cameraModel == null)
            {
                if (externalViewMatrixSupplier == null)
                {
                    MathUtils.setIdentity4x4(viewMatrix);
                    return viewMatrix;
                }
                return externalViewMatrixSupplier.get();
            }
            cameraModel.computeViewMatrix(viewMatrix);
            return viewMatrix;
        };
    }
    
    /**
     * Create a supplier for the projection matrix, as a float array with 16 
     * elements, containing the projection matrix in column-major order.<br>
     * <br>
     * The resulting supplier will supply a projection matrix as follows:
     * <ul>
     *   <li> 
     *     If a non-<code>null</code> {@link #setCurrentCameraModel
     *     current camera model} has been set, then the projection matrix from
     *     this {@link CameraModel} will be returned
     *   </li>
     *   <li>
     *     Otherwise, if the external matrix supplier is non-<code>null</code>,
     *     then its matrix will be returned
     *   </li>
     *   <li>
     *     Otherwise, an identity matrix will be returned
     *   </li>
     * </ul>
     * Note: The supplier MAY always return the same array instance.
     * Callers MUST NOT store or modify the returned array. 
     * 
     * @param externalProjectionMatrixSupplier The optional external projection
     * matrix supplier that may have been given in the constructor
     * @return The view matrix supplier
     */
    private Supplier<float[]> createProjectionMatrixSupplier(
        Supplier<float[]> externalProjectionMatrixSupplier)
    {
        float[] projectionMatrix = MathUtils.createIdentity4x4();
        return () ->
        {
            CameraModel cameraModel = currentCameraModelSupplier.get();
            if (cameraModel == null)
            {
                if (externalProjectionMatrixSupplier == null)
                {
                    MathUtils.setIdentity4x4(projectionMatrix);
                    return projectionMatrix;
                }
                return externalProjectionMatrixSupplier.get();
            }
            Float aspectRatio = null;
            if (aspectRatioSupplier != null)
            {
                aspectRatio = (float)aspectRatioSupplier.getAsDouble();
            }
            cameraModel.computeProjectionMatrix(
                projectionMatrix, aspectRatio);
            return projectionMatrix;
        };
    }
    
    /**
     * Returns the viewport that is used for rendering, as an array
     * [x, y, width, height]
     * 
     * @return The viewport
     */
    public float[] getViewport()
    {
        return viewportSupplier.get();
    }
    
    /**
     * Returns the view matrix, as an array with 16 elements, containing 
     * the matrix in column-major order.<br>
     * <br> 
     * This method MAY always return the same array instance.
     * Callers MUST NOT store or modify the returned array.
     * 
     * @return The view matrix
     */
    public float[] getViewMatrix()
    {
        return viewMatrixSupplier.get();
    }
    
    /**
     * Returns the projection matrix, as an array with 16 elements, containing 
     * the matrix in column-major order.<br>
     * <br> 
     * This method MAY always return the same array instance.
     * Callers MUST NOT store or modify the returned array.
     * 
     * @return The view matrix
     */
    public float[] getProjectionMatrix()
    {
        return projectionMatrixSupplier.get();
    }
}