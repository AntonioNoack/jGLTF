package de.javagl.jgltf.model.animation;

import java.util.Arrays;

/**
 * Methods to compute {@link Interpolator} keys from a given value and a
 * (sorted) float array
 */
class InterpolatorKeys {
    /**
     * Compute the index of the segment that the given key belongs to.
     * If the given key is smaller than the smallest or larger than
     * the largest key, then 0 or <code>keys.length-1<code> will be returned,
     * respectively.
     *
     * @param key  The key
     * @param keys The sorted keys
     * @return The index for the key
     */
    public static int computeIndex(float key, float[] keys) {
        int index = Arrays.binarySearch(keys, key);
        if (index >= 0) {
            return index;
        }
        return Math.max(0, -index - 2);
    }

    /**
     * Compute the alpha value for the given key. This is a value in [0,1],
     * describing the relative location of the key in the segment with the
     * given index.
     *
     * @param key   The key
     * @param keys  The sorted keys
     * @param index The index of the key
     * @return The alpha value
     */
    public static float computeAlpha(float key, float[] keys, int index) {
        if (key <= keys[0] || keys.length < 2) {
            return 0.0f;
        }
        // fix, because keys.length may be 1, and then you'd get an array out of bounds exception in line 47 (keys[index+1])
        if (key >= keys[keys.length - 1] || index >= keys.length - 1) {
            return 1.0f;
        }
        float local = key - keys[index];
        float delta = keys[index + 1] - keys[index];
        return local / delta;

    }

    /**
     * A basic test
     *
     * @param args Not used
     */
    public static void main(String[] args) {
        float[] keys = {1, 8, 11};
        for (float d = -1; d <= 12; d += 0.1) {
            int index = computeIndex(d, keys);
            float alpha = computeAlpha(d, keys, index);
            System.out.println("For " + d);
            System.out.println("    index " + index);
            System.out.println("    alpha " + alpha);
        }
    }

}
