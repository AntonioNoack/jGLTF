package de.javagl.jgltf.model;

import java.nio.ByteBuffer;

/**
 * Interface for a buffer view, which represents a slice of a
 * {@link BufferModel}
 */
public interface BufferViewModel extends NamedModelElement {
    /**
     * Return the actual data that this view stands for. This will be a new
     * slice of the buffer of the data that is stored internally. Changes
     * in the position or limit of this buffer will not affect this model
     *
     * @return The buffer view data
     */
    ByteBuffer getBufferViewData();

    /**
     * Returns the {@link BufferModel} that this view refers to
     *
     * @return The {@link BufferModel}
     */
    BufferModel getBufferModel();

    /**
     * Returns the offset of this view referring to the buffer
     *
     * @return The offset, in bytes
     */
    int getByteOffset();

    /**
     * Returns the length of this view, in bytes
     *
     * @return The length, in bytes
     */
    int getByteLength();

    /**
     * Returns the stride between two consecutive elements of this buffer view,
     * in bytes. If this is <code>null</code>, then the elements are tightly
     * packed.
     *
     * @return The stride, in bytes
     */
    Integer getByteStride();

    /**
     * Returns the (optional) target that this buffer should be bound to.
     * If this is not <code>null</code>, then it will be the GL constant for
     * <code>GL_ARRAY_BUFFER</code> or <code>GL_ELEMENT_ARRAY_BUFFER</code>.
     *
     * @return The target, or <code>null</code>
     */
    Integer getTarget();
}