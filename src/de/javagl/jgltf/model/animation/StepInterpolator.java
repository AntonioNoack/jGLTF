package de.javagl.jgltf.model.animation;

/**
 * Implementation of an {@link Interpolator} that interpolates stepwise.
 */
public class StepInterpolator implements Interpolator {
    @Override
    public void interpolate(
            float[] a, float[] b, float alpha, float[] result) {
        System.arraycopy(a, 0, result, 0, a.length);
    }

}
