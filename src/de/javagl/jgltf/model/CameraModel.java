package de.javagl.jgltf.model;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;


/**
 * An interface for a camera that is attached to a {@link NodeModel}
 */
public interface CameraModel extends NamedModelElement {
    /**
     * Returns the name of this camera model instance, suitable to be shown
     * to a user. In contrast to the pure {@link #getName() name}, this method
     * will return a string that also contains information about the node
     * that this camera instance is attached to.
     *
     * @return The name of this camera model instance
     */
    String getInstanceName();

    /**
     * Returns the {@link NodeModel} that the camera is attached to
     *
     * @return The {@link NodeModel}
     */
    NodeModel getNodeModel();

    /**
     * Compute the view matrix for this camera. This is the inverse of the
     * global transform of the node that the camera is attached to.<br>
     * <br>
     * The result will be written to the given array, as a 4x4 matrix in
     * column major order. If the given array is <code>null</code> or does
     * not have a length of 16, then a new array with length 16 will be
     * created and returned.
     *
     * @param result The result array
     * @return The result array
     */
    float[] computeViewMatrix(float[] result);

    /**
     * Compute the projection matrix for this camera.<br>
     * <br>
     * The result will be written to the given array, as a 4x4 matrix in
     * column major order. If the given array is <code>null</code> or does
     * not have a length of 16, then a new array with length 16 will be
     * created and returned.
     *
     * @param result      The result array
     * @param aspectRatio An optional aspect ratio that should be used.
     *                    If this is <code>null</code>, then the aspect ratio of the
     *                    camera will be used.
     * @return The result array
     */
    float[] computeProjectionMatrix(float[] result, Float aspectRatio);

    /**
     * Create the supplier of the view matrix for this camera model.<br>
     * <br>
     * The matrix will be provided as a float array with 16 elements,
     * storing the matrix entries in column-major order.<br>
     * <br>
     * Note: The supplier MAY always return the same array instance.
     * Callers MUST NOT store or modify the returned array.
     *
     * @return The supplier.
     */
    Supplier<float[]> createViewMatrixSupplier();

    /**
     * Create the supplier of the projection matrix for this camera model.<br>
     * <br>
     * The matrix will be provided as a float array with 16 elements,
     * storing the matrix entries in column-major order.<br>
     * <br>
     * Note: If the type of the camera that this {@link CameraModel} was
     * created for is neither <code>"perspective"</code> nor
     * <code>"orthographic"</code>,
     * then the supplier will print an error message and return
     * the identity matrix.
     *
     * @param aspectRatioSupplier The optional supplier for the aspect
     *                            ratio of the camera. If this is <code>null</code>, then the
     *                            aspect ratio of the camera will be used.
     * @return The supplier
     */
    Supplier<float[]> createProjectionMatrixSupplier(
            DoubleSupplier aspectRatioSupplier);

}