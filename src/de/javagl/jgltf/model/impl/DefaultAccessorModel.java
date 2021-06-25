package de.javagl.jgltf.model.impl;

import de.javagl.jgltf.model.*;

/**
 * Implementation of an {@link AccessorModel}
 */
public final class DefaultAccessorModel extends AbstractNamedModelElement
        implements AccessorModel {
    /**
     * The component type, as a GL constant
     */
    private final int componentType;

    /**
     * The offset in bytes, referring to the buffer view
     */
    private int byteOffset;

    /**
     * The {@link BufferViewModel} for this model
     */
    private BufferViewModel bufferViewModel;

    /**
     * The {@link ElementType} of this accessor
     */
    private final ElementType elementType;

    /**
     * The number of elements
     */
    private final int count;

    /**
     * The stride between the start of one element and the next
     */
    private int byteStride;

    /**
     * The {@link AccessorData}
     */
    private AccessorData accessorData;

    /**
     * The minimum components
     */
    private Number[] max;

    /**
     * The maximum components
     */
    private Number[] min;

    /**
     * Creates a new instance
     *
     * @param componentType The component type GL constant
     * @param count         The number of elements
     * @param elementType   The element type
     */
    public DefaultAccessorModel(
            int componentType,
            int count,
            ElementType elementType) {
        this.componentType = componentType;
        this.count = count;
        this.elementType = elementType;
    }

    /**
     * Set the {@link BufferViewModel} for this model
     *
     * @param bufferViewModel The {@link BufferViewModel}
     */
    public void setBufferViewModel(BufferViewModel bufferViewModel) {
        this.bufferViewModel = bufferViewModel;
    }

    /**
     * Set the byte offset, referring to the {@link BufferViewModel}
     *
     * @param byteOffset The byte offset
     */
    public void setByteOffset(int byteOffset) {
        this.byteOffset = byteOffset;
    }

    /**
     * Set the byte stride, indicating the number of bytes between the start
     * of one element and the start of the next element
     *
     * @param byteStride The byte stride
     */
    public void setByteStride(int byteStride) {
        this.byteStride = byteStride;
    }

    @Override
    public BufferViewModel getBufferViewModel() {
        return bufferViewModel;
    }

    @Override
    public int getComponentType() {
        return componentType;
    }

    @Override
    public Class<?> getComponentDataType() {
        return Accessors.getDataTypeForAccessorComponentType(
                getComponentType());
    }

    @Override
    public int getComponentSizeInBytes() {
        return Accessors.getNumBytesForAccessorComponentType(componentType);
    }

    @Override
    public int getElementSizeInBytes() {
        return elementType.getNumComponents() * getComponentSizeInBytes();
    }

    @Override
    public int getByteOffset() {
        return byteOffset;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public ElementType getElementType() {
        return elementType;
    }

    @Override
    public int getByteStride() {
        return byteStride;
    }

    @Override
    public AccessorData getAccessorData() {
        if (accessorData == null) {
            accessorData = AccessorDatas.create(this);
        }
        return accessorData;
    }


    @Override
    public Number[] getMin() {
        if (min == null) {
            min = AccessorDatas.computeMin(getAccessorData());
        }
        return min.clone();
    }

    @Override
    public Number[] getMax() {
        if (max == null) {
            max = AccessorDatas.computeMax(getAccessorData());
        }
        return max.clone();
    }

}
