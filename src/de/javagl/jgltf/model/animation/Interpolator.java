package de.javagl.jgltf.model.animation;

/**
 * #ANNO no reason for it to be private
 * Package-private interface for classes that can interpolate between
 * (equal-length) arrays of <code>float</code> values
 */
public interface Interpolator {
    /**
     * Interpolate between <code>a</code> and <code>b</code>, based on
     * the given alpha value (that is usually in [0,1]), and place the
     * results in the given result array. None of the given arrays may
     * be <code>null</code>, and they must all have the same length.
     *
     * @param a      The first array
     * @param b      The second array
     * @param alpha  The interpolation value
     * @param result The array that will store the result
     * @throws NullPointerException      If any argument is <code>null</code>
     * @throws IndexOutOfBoundsException May be thrown if the arrays do not
     *                                   have the same length
     */
    public void interpolate(float[] a, float[] b, float alpha, float[] result);
}
