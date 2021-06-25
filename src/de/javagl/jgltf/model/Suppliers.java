package de.javagl.jgltf.model;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * Utility methods to create Supplier instances
 */
public class Suppliers {
    /**
     * Create a supplier of a 4x4 matrix that is computed by applying
     * the given computer to the given object and a 16-element array.<br>
     * <br>
     * If the given object is <code>null</code>, then the identity
     * matrix will be supplied.<br>
     * <br>
     * Note: The supplier MAY always return the same array instance.
     * Callers MUST NOT store or modify the returned array.
     *
     * @param <T>      The object type
     * @param object   The object
     * @param computer The computer function
     * @return The supplier
     */
    public static <T> Supplier<float[]> createTransformSupplier(
            T object, BiConsumer<T, float[]> computer) {
        float[] transform = new float[16];
        if (object == null) {
            return () ->
            {
                MathUtils.setIdentity4x4(transform);
                return transform;
            };
        }
        return () ->
        {
            computer.accept(object, transform);
            return transform;
        };
    }


    /**
     * Private constructor to prevent instantiation
     */
    private Suppliers() {
        // Private constructor to prevent instantiation
    }
}
