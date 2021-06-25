package de.javagl.jgltf.viewer;

import de.javagl.jgltf.model.CameraModel;
import de.javagl.jgltf.model.GltfModel;

/**
 * Interface for a rendered {@link GltfModel}
 */
interface RenderedGltfModel
{
    /**
     * Render this instance. This is assumed to be called on the GL thread.
     */
    void render();

    /**
     * Set the {@link CameraModel} that should be used for rendering. 
     * If the given {@link CameraModel} is <code>null</code>,
     * then the external camera will be used.<br>
     * <br>
     * @param cameraModel The {@link CameraModel} 
     */
    void setCurrentCameraModel(CameraModel cameraModel);

    /**
     * Delete this object by removing its GL data from the {@link GlContext}.
     * <br>
     * After this method has been called, attempting to {@link #render()}
     * this object will result in a warning to be printed.
     */
    void delete();

    
}
