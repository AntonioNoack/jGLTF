package de.javagl.jgltf.model.animation;

/**
 * An {@link Interpolator} that performs a spherical linear interpolation
 * (SLERP) between quaternions, given as arrays of length 4
 */
public class SlerpQuaternionInterpolator implements Interpolator {

    @Override
    public void interpolate(float[] a, float[] b, float alpha, float[] result) {

        // Adapted from javax.vecmath.Quat4f
        float ax = a[0];
        float ay = a[1];
        float az = a[2];
        float aw = a[3];
        float bx = b[0];
        float by = b[1];
        float bz = b[2];
        float bw = b[3];

        float dot = ax * bx + ay * by + az * bz + aw * bw;
        if (dot < 0) {
            bx = -bx;
            by = -by;
            bz = -bz;
            bw = -bw;
            dot = -dot;
        }
        float epsilon = 1e-6f;
        float s0, s1;
        if ((1.0 - dot) > epsilon) {
            float omega = (float) Math.acos(dot);
            float invSinOmega = 1.0f / (float) Math.sin(omega);
            s0 = (float) Math.sin((1.0 - alpha) * omega) * invSinOmega;
            s1 = (float) Math.sin(alpha * omega) * invSinOmega;
        } else {
            s0 = 1.0f - alpha;
            s1 = alpha;
        }
        float rx = s0 * ax + s1 * bx;
        float ry = s0 * ay + s1 * by;
        float rz = s0 * az + s1 * bz;
        float rw = s0 * aw + s1 * bw;
        result[0] = rx;
        result[1] = ry;
        result[2] = rz;
        result[3] = rw;
    }

}
