package de.javagl.jgltf.viewer.lwjgl;

import de.javagl.jgltf.viewer.AbstractGltfViewer;
import de.javagl.jgltf.viewer.GlContext;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.awt.AWTGLCanvas;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import de.javagl.jgltf.logging.Logger;

import static org.lwjgl.opengl.GL11.*;

/**
 * Implementation of a glTF viewer based on LWJGL
 */
public class GltfViewerLwjgl extends AbstractGltfViewer<Component> {
    /**
     * The logger used in this class
     */
    private static final Logger logger =
            Logger.getLogger(GltfViewerLwjgl.class);

    /**
     * The AWTGLCanvas, i.e. the rendering component of this renderer
     */
    private Component glComponent;

    /**
     * The {@link GlContext}
     */
    private final GlContextLwjgl glContext;

    /**
     * Whether the component was resized, and glViewport has to be called
     */
    private boolean viewportNeedsUpdate = true;

    /**
     * Creates a new GltfViewerJogl
     */
    public GltfViewerLwjgl() {
        try {
            this.glComponent = new AWTGLCanvas() {
                /**
                 * Serial UID
                 */
                private static final long serialVersionUID = 1L;

                @Override
                public void initGL() {
                    // correct?
                    // #anno modified, because the libraries seem to have changed
                    System.out.println("initGL()");
                    GL.createCapabilities();
                }

                @Override
                public void paintGL() {
                    if (viewportNeedsUpdate) {
                        glViewport(0, 0, getWidth(), getHeight());
                        viewportNeedsUpdate = false;
                    }
                    doRender();
                    try {
                        swapBuffers();
                    } catch (Exception e) {
                        logger.severe("Could not swap buffers");
                    }
                }

            };

            this.glComponent.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    viewportNeedsUpdate = true;
                }
            });
        } catch (Exception e) {
            logger.severe("Could not create AWTGLCanvas");
            this.glComponent = new Canvas();
        }

        // Without setting the minimum size, the canvas cannot 
        // be resized when it is embedded in a JSplitPane
        this.glComponent.setMinimumSize(new Dimension(10, 10));

        this.glContext = new GlContextLwjgl();
    }

    @Override
    public GlContext getGlContext() {
        return glContext;
    }

    @Override
    public Component getRenderComponent() {
        return glComponent;
    }

    @Override
    public int getWidth() {
        return glComponent.getWidth();
    }

    @Override
    public int getHeight() {
        return glComponent.getHeight();
    }

    @Override
    public void triggerRendering() {
        if (getRenderComponent() != null) {
            getRenderComponent().repaint();
        }
    }

    @Override
    protected void prepareRender() {
        // Nothing to do here
    }

    @Override
    protected void render() {
        // Enable the color and depth mask explicitly before calling glClear.
        // When they are not enabled, they will not be cleared!
        glColorMask(true, true, true, true);
        glDepthMask(true);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        renderGltfModels();
    }

}
