package de.javagl.jgltf.model.animation;

/**
 * Interface for classes that want to be informed about the progress
 * of an {@link Animation}
 */
public interface AnimationListener {
    /**
     * Will be called when the given {@link Animation} was updated.<br>
     * <br>
     * <b>Note:</b> The given array of interpolated output values MAY
     * be reused for multiple calls. Implementors of this method MUST NOT
     * store or modify the given array.
     *
     * @param source The source {@link Animation}
     * @param timeS  The time, in seconds
     * @param values The interpolated values for the given time
     */
    void animationUpdated(
            Animation source, float timeS, float[] values);
}