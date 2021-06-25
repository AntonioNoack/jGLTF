package de.javagl.jgltf.model.impl;

import de.javagl.jgltf.model.BufferModel;
import de.javagl.jgltf.model.BufferViewModel;
import de.javagl.jgltf.model.io.Buffers;

import java.nio.ByteBuffer;
import java.util.function.Consumer;

/**
 * Implementation of a {@link BufferViewModel}
 */
public final class DefaultBufferViewModel extends AbstractNamedModelElement
        implements BufferViewModel {
    /**
     * The {@link BufferModel} for this model
     */
    private BufferModel bufferModel;

    /**
     * The byte offset
     */
    private int byteOffset;

    /**
     * The byte length
     */
    private int byteLength;

    /**
     * The byte stride
     */
    private Integer byteStride;

    /**
     * The optional target
     */
    private final Integer target;

    /**
     * An optional callback that will be used to perform the
     * substitution of sparse accessor data in the
     * {@link #getBufferViewData() buffer view data}
     * when it is obtained for the first time.
     */
    private Consumer<? super ByteBuffer> sparseSubstitutionCallback;

    /**
     * Whether the sparse substitution was already applied
     */
    private boolean sparseSubstitutionApplied;

    /**
     * Creates a new instance
     *
     * @param target The optional target
     */
    public DefaultBufferViewModel(Integer target) {
        this.byteOffset = 0;
        this.byteLength = 0;
        this.target = target;
    }

    /**
     * Set the callback that will perform the substitution of sparse accessor
     * data in the {@link #getBufferViewData() buffer view data} when it is
     * obtained for the first time.
     *
     * @param sparseSubstitutionCallback The callback
     */
    public void setSparseSubstitutionCallback(
            Consumer<? super ByteBuffer> sparseSubstitutionCallback) {
        this.sparseSubstitutionCallback = sparseSubstitutionCallback;
    }

    /**
     * Set the {@link BufferModel} for this model
     *
     * @param bufferModel The {@link BufferModel}
     */
    public void setBufferModel(BufferModel bufferModel) {
        this.bufferModel = bufferModel;
    }

    /**
     * Set the byte offset of this view referring to its {@link BufferModel}
     *
     * @param byteOffset The byte offset
     */
    public void setByteOffset(int byteOffset) {
        this.byteOffset = byteOffset;
    }

    /**
     * Set the byte length of this buffer view
     *
     * @param byteLength The byte length
     */
    public void setByteLength(int byteLength) {
        this.byteLength = byteLength;
    }

    /**
     * Set the optional byte stride. This byte stride must be
     * non-<code>null</code> if more than one accessor refers
     * to this buffer view.
     *
     * @param byteStride The byte stride
     */
    public void setByteStride(Integer byteStride) {
        this.byteStride = byteStride;
    }


    @Override
    public ByteBuffer getBufferViewData() {
        ByteBuffer bufferData = bufferModel.getBufferData();
        ByteBuffer bufferViewData =
                Buffers.createSlice(bufferData, getByteOffset(), getByteLength());
        if (sparseSubstitutionCallback != null && !sparseSubstitutionApplied) {
            sparseSubstitutionCallback.accept(bufferViewData);
            sparseSubstitutionApplied = true;
        }
        return bufferViewData;
    }

    @Override
    public BufferModel getBufferModel() {
        return bufferModel;
    }

    @Override
    public int getByteOffset() {
        return byteOffset;
    }

    @Override
    public int getByteLength() {
        return byteLength;
    }

    @Override
    public Integer getByteStride() {
        return byteStride;
    }

    @Override
    public Integer getTarget() {
        return target;
    }

}
