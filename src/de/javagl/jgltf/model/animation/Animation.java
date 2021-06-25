package de.javagl.jgltf.model.animation;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A class representing a generic animation. The animation consists of
 * a mapping between time key frames and values, and allows
 * {@link AnimationListener}s to be informed about the progress of the
 * animation, including the current time and the (interpolated) values
 * for this time point.
 */
public final class Animation {

    /**
     * The key frame times, in seconds
     */
    public float[] timesS;

    /**
     * The values. Each element of this array corresponds to one key
     * frame time
     */
    public float[][] values;

    /**
     * The interpolator for the values
     */
    public Interpolator interpolator;

    /**
     * A pre-allocated array of the output values that will be passed
     * to the listeners. The listeners are not allowed to store or
     * modify this array.
     */
    public float[] outputValues;

    /**
     * The {@link AnimationListener}s that are informed about the progress
     * of this animation
     */
    private final List<AnimationListener> listeners;

    /**
     * Creates a new animation with the given time key frames and the
     * corresponding values.
     *
     * @param timesS           The time key frames, in seconds
     * @param values           The values. Each element of this array consists of the
     *                         values for the corresponding key frame time.
     * @param interpolatorType The {@link InterpolatorType} that will be
     *                         used for interpolating the values
     * @throws NullPointerException     If either of the given parameters is
     *                                  <code>null</code>, or the given values array contains <code>null</code>
     *                                  elements
     * @throws IllegalArgumentException If any of the given arrays has a
     *                                  length of 0
     * @throws IllegalArgumentException If any of the given values arrays
     *                                  has a length that is different from the length of the times array.
     */
    public Animation(
            float[] timesS,
            float[][] values,
            InterpolatorType interpolatorType) {
        Objects.requireNonNull(timesS, "The times may not be null");
        Objects.requireNonNull(values, "The values may not be null");
        if (timesS.length == 0) {
            throw new IllegalArgumentException(
                    "The keys may not have a length of 0");
        }
        if (values.length != timesS.length) {
            throw new IllegalArgumentException(
                    "The values must have a length of " + timesS.length + ", " +
                            "but have a length of " + values.length);
        }
        this.timesS = timesS.clone();
        this.values = new float[values.length][];
        for (int i = 0; i < values.length; i++) {
            this.values[i] = values[i].clone();
        }
        this.outputValues = new float[values[0].length];
        this.interpolator = Interpolators.create(interpolatorType);
        this.listeners = new CopyOnWriteArrayList<>();
    }


    /**
     * Returns the start time of this animation, in seconds
     *
     * @return The start time
     */
    public float getStartTimeS() {
        return timesS[0];
    }

    /**
     * Returns the end time of this animation, in seconds
     *
     * @return The end time
     */
    public float getEndTimeS() {
        return timesS[timesS.length - 1];
    }

    /**
     * Returns the duration of this animation, in seconds
     *
     * @return The duration
     */
    public float getDurationS() {
        return getEndTimeS() - getStartTimeS();
    }

    /**
     * Add the given {@link AnimationListener} to be informed about any
     * progress in this animation
     *
     * @param listener The listener to add
     */
    public void addAnimationListener(AnimationListener listener) {
        listeners.add(listener);
    }

    /**
     * Remove the given {@link AnimationListener}
     *
     * @param listener The listener to remove
     */
    public void removeAnimationListener(AnimationListener listener) {
        listeners.remove(listener);
    }

    /**
     * #ANNO, wtf, why is this private?? I need full control
     * Package-private method to update this animation based on the given time,
     * and inform all registered listeners.
     *
     * @param timeS The time, in seconds
     */
    public void update(float timeS) {
        int index0 = InterpolatorKeys.computeIndex(timeS, timesS);
        int index1 = Math.min(timesS.length - 1, index0 + 1);
        float alpha = InterpolatorKeys.computeAlpha(timeS, timesS, index0);

        // System.out.println("For "+timeS+" in "+Arrays.toString(timesS));
        // System.out.println("index0 "+index0);
        // System.out.println("index1 "+index1);
        // System.out.println("alpha  "+alpha);

        float[] a = values[index0];
        float[] b = values[index1];
        interpolator.interpolate(a, b, alpha, outputValues);
        for (AnimationListener listener : listeners) {
            listener.animationUpdated(this, timeS, outputValues);
        }
    }

}