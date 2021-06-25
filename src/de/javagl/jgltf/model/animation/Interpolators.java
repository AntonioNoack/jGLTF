package de.javagl.jgltf.model.animation;

/**
 * Methods to create {@link Interpolator} instances
 */
public class Interpolators {
    /**
     * Creates an {@link Interpolator} for the given {@link InterpolatorType}.
     * If the given {@link InterpolatorType} is <code>null</code>, then
     * a {@link InterpolatorType#LINEAR linear} interpolator will be returned.
     *
     * @param interpolatorType The {@link InterpolatorType}
     * @return The {@link Interpolator}
     */
    public static Interpolator create(InterpolatorType interpolatorType) {
        if (interpolatorType == null) {
            return new LinearInterpolator();
        }
        switch (interpolatorType) {
            case SLERP:
                return new SlerpQuaternionInterpolator();

            case LINEAR:
                return new LinearInterpolator();

            case STEP:
                return new StepInterpolator();

            default:
                throw new IllegalArgumentException(
                        "Invalid interpolator type: " + interpolatorType);
        }
    }

    /**
     * Private constructor to prevent instantiation
     */
    private Interpolators() {
        // Private constructor to prevent instantiation
    }
}
