package de.javagl.jgltf.model.animation;

/**
 * Implementation of an {@link Interpolator} that interpolates linearly.
 * Hence the name.
 */
public class LinearInterpolator implements Interpolator {
    @Override
    public void interpolate(
            float[] a, float[] b, float alpha, float[] result) {
        for (int i = 0; i < a.length; i++) {
            float ai = a[i];
            float bi = b[i];
            float ri = ai + alpha * (bi - ai);
            result[i] = ri;
        }

    }

}
