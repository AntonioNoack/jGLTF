package de.javagl.jgltf.model.impl.creation;

/**
 * Utility methods of the buffer structure creation
 */
class Utils {
    /**
     * Computes the greatest common divisor of the given arguments
     *
     * @param a The first argument
     * @param b The second argument
     * @return The greatest common divisor
     */
    private static int computeGreatestCommonDivisor(int a, int b) {
        return b == 0 ? a : computeGreatestCommonDivisor(b, a % b);
    }

    /**
     * Computes the least common multiple of the given arguments
     *
     * @param a The first argument
     * @param b The second argument
     * @return The least common multiple
     */
    static int computeLeastCommonMultiple(int a, int b) {
        if (a == 0) {
            return b;
        }
        if (b == 0) {
            return a;
        }
        return (a * b) / computeGreatestCommonDivisor(a, b);
    }

    /**
     * Compute the padding that has to be added to the given size, in order
     * to achieve the given alignment
     *
     * @param size      The size
     * @param alignment The alignment
     * @return The padding
     */
    static int computePadding(int size, int alignment) {
        int remainder = size % alignment;
        if (remainder > 0) {
            return alignment - remainder;
        }
        return 0;
    }

    /**
     * Private constructor to prevent instantiation
     */
    private Utils() {
        // Private constructor to prevent instantiation
    }

}
