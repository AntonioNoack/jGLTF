package de.javagl.jgltf.model;

/**
 * Interface for a data accessor
 */
public interface AccessorModel extends NamedModelElement {
    /**
     * Returns the {@link BufferViewModel} for the data that this accessor
     * provides access to. Typed access to the data is possible by obtaining
     * the {@link AccessorData} using {@link #getAccessorData()}.
     *
     * @return The {@link BufferViewModel}
     */
    BufferViewModel getBufferViewModel();

    /**
     * Returns the component type of this accessor, as a GL constant. For
     * example, <code>GL_FLOAT</code> or <code>GL_UNSIGNED_SHORT</code>
     *
     * @return The component type
     */
    int getComponentType();

    /**
     * Returns the data type of the components of this accessor. For example,
     * <code>float.class</code> or <code>short.class</code>.
     *
     * @return The component data type
     */
    Class<?> getComponentDataType();

    /**
     * Returns the size of one component, in bytes
     *
     * @return The component size, in bytes
     */
    int getComponentSizeInBytes();

    /**
     * Returns the size of one element, in bytes
     *
     * @return The element size, in bytes
     */
    int getElementSizeInBytes();

    /**
     * Returns the byte offset of this accessor referring to its
     * {@link BufferViewModel}
     *
     * @return The byte offset
     */
    int getByteOffset();

    /**
     * Returns the number of elements that this accessor provides access to
     *
     * @return The number of elements
     */
    int getCount();

    /**
     * Returns the {@link ElementType} that this accessor provides access to
     *
     * @return The {@link ElementType}
     */
    ElementType getElementType();

    /**
     * Returns the byte stride between the starts of two consecutive elements
     * of this accessor.
     *
     * @return The byte stride
     */
    int getByteStride();

    /**
     * Returns the {@link AccessorData} for this accessor. The exact type
     * of the returned {@link AccessorData} object will depend on the
     * {@link #getComponentDataType() component data type}. It will be
     * {@link AccessorByteData}, {@link AccessorShortData},
     * {@link AccessorIntData} or {@link AccessorFloatData} for a component
     * data type of <code>byte.class</code>, <code>short.class</code>,
     * <code>int.class</code> or <code>float.class</code>, respectively,
     * and can be safely cast to the respective type.
     *
     * @return The {@link AccessorData}
     */
    AccessorData getAccessorData();

    /**
     * Returns the minimum components of the {@link AccessorData}. The
     * returned array will be a clone of the array that is stored internally,
     * and have a length that matches the {@link ElementType#getNumComponents()
     * number of components per element}.
     *
     * @return The minimum components
     */
    Number[] getMin();

    /**
     * Returns the maximum components of the {@link AccessorData}. The
     * returned array will be a clone of the array that is stored internally,
     * and have a length that matches the {@link ElementType#getNumComponents()
     * number of components per element}.
     *
     * @return The maximum components
     */
    Number[] getMax();
}
