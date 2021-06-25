package de.javagl.jgltf.viewer;

/**
 * An interface for an external camera. An instance of a class implementing
 * this interface may be passed to 
 * {@link AbstractGltfViewer#setExternalCamera(ExternalCamera)}, to allow
 * the camera of the viewer to be controlled externally, alternatively to
 * the cameras that are provided by the glTF.
 */
public interface ExternalCamera
{
    /**
     * The view matrix of this camera, as a float array with 16 elements, 
     * representing the 4x4 matrix in column-major order.<br>
     * <br>
     * The returned matrix will not be stored or modified. So the supplier
     * may always return the same matrix instance.
     * 
     * @return The view matrix
     */
    float[] getViewMatrix();

    /**
     * The projection matrix of this camera, as a float array with 16 elements,
     * representing the 4x4 matrix in column-major order.<br>
     * <br>
     * The returned matrix will not be stored or modified. So the supplier
     * may always return the same matrix instance.
     * 
     * @return The projection matrix
     */
    float[] getProjectionMatrix();
}