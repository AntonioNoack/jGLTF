package de.javagl.jgltf.viewer;

import java.util.function.Supplier;

/**
 * A supplier where the supplied value may be set
 * 
 * @param <T> The type of the value
 */
class SettableSupplier<T> implements Supplier<T>
{
    /**
     * The value 
     */
    private T value;

    /**
     * Create a new supplier that initially supplies <code>null</code>.
     */
    SettableSupplier()
    {
        this(null);
    }
    
    /**
     * Create a new supplier for the given value
     * 
     * @param value The value
     */
    private SettableSupplier(T value)
    {
        this.value = value;
    }
    
    /**
     * Set the value
     * 
     * @param value The value
     */
    void set(T value)
    {
        this.value = value;
    }
    
    @Override
    public T get()
    {
        return value;
    }
    
}