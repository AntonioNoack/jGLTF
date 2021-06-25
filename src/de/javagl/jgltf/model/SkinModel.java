package de.javagl.jgltf.model;

import java.util.List;

/**
 * Interface for a skin of a glTF asset
 */
public interface SkinModel extends NamedModelElement {
    /**
     * Provides the bind shape matrix of the skin.<br>
     * <br>
     * The result will be written to the given array, as a 4x4 matrix in
     * column major order. If the given array is <code>null</code> or does
     * not have a length of 16, then a new array with length 16 will be
     * created and returned.
     *
     * @param result The result array
     * @return The result array
     */
    float[] getBindShapeMatrix(float[] result);

    /**
     * Returns an unmodifiable list containing the joint nodes of the skeleton
     *
     * @return The joint nodes
     */
    List<NodeModel> getJoints();

    /**
     * Returns the skeleton root node. If the return value is <code>null</code>,
     * then joint transforms refer to the scene root.
     *
     * @return The skeleton node
     */
    NodeModel getSkeleton();

    /**
     * Returns the {@link AccessorModel} that provides the data for the
     * inverse bind matrices, one for each {@link #getJoints() joint}
     *
     * @return The inverse bind matrices accessor
     */
    AccessorModel getInverseBindMatrices();

    /**
     * Convenience function to obtain the inverse bind matrix for the joint
     * with the given index.<br>
     * <br>
     * The result will be written to the given array, as a 4x4 matrix in
     * column major order. If the given array is <code>null</code> or does
     * not have a length of 16, then a new array with length 16 will be
     * created and returned.
     *
     * @param index  The index of the joint
     * @param result The result array
     * @return The result array
     */
    float[] getInverseBindMatrix(int index, float[] result);
}
