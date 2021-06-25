package de.javagl.jgltf.model;

/**
 * Utility methods. These should not be considered as part of the public API.
 */
public class Utils {
    /**
     * Validate that the given array is not <code>null</code> and has the
     * given length. If this is not the case, return a new array with the
     * specified length.
     *
     * @param array  The array
     * @param length The length
     * @return The array, or a new array with the desired length
     */
    public static float[] validate(float[] array, int length) {
        if (array != null && array.length == length) {
            return array;
        }
        return new float[length];
    }

    /**
     * Private constructor to prevent instantiation
     */
    private Utils() {
        // Private constructor to prevent instantiation
    }

}
