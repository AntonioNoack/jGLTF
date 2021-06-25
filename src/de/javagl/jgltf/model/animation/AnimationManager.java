package de.javagl.jgltf.model.animation;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A class that manages several {@link Animation} instances, and dispatches
 * any updates in the (global) time to these animations.
 */
public final class AnimationManager {
    /**
     * A policy describing how the animations should be executed
     */
    public enum AnimationPolicy {
        /**
         * Indicates that the animations should be executed only
         * once, and then removed from the {@link AnimationManager}
         */
        ONCE,

        /**
         * Indicates that the animations should be executed in
         * a ping-pong fashion
         */
        PING_PONG,

        /**
         * Indicates that the animations should be looped
         */
        LOOP
    }

    /**
     * The {@link AnimationPolicy}
     */
    public AnimationPolicy animationPolicy;

    /**
     * The start time, in nanoseconds
     */
    private long startNs;

    /**
     * The current time, in nanoseconds
     */
    private long currentNs;

    /**
     * The list of {@link Animation} instances maintained by this manager
     */
    private final List<Animation> animations;

    /**
     * The maximum {@link Animation#getEndTimeS()} of all animations
     */
    private float maxEndTimeS;

    /**
     * The list of {@link AnimationManagerListener}s that want to be
     * informed about changes in this manager
     */
    private final List<AnimationManagerListener> animationManagerListeners;

    /**
     * Creates a new, empty animation manager
     *
     * @param animationPolicy The {@link AnimationPolicy}
     */
    public AnimationManager(AnimationPolicy animationPolicy) {
        this.animationPolicy = animationPolicy;
        this.startNs = System.nanoTime();
        this.currentNs = startNs;
        this.animations = new CopyOnWriteArrayList<>();
        this.maxEndTimeS = 0.0f;
        this.animationManagerListeners =
                new CopyOnWriteArrayList<>();
    }

    /**
     * Reset this manager to its initial state. This will also update
     * all {@link Animation}s with a time of 0.0.
     */
    public void reset() {
        startNs = System.nanoTime();
        currentNs = System.nanoTime();
        performStep(0);
    }

    /**
     * Returns the current animation time, in seconds
     *
     * @return The animation time
     */
    public float getCurrentTimeS() {
        long timeNs = currentNs - startNs;
        return timeNs * 1e-9f;
    }

    /**
     * Add the given {@link Animation} to this manager.
     *
     * @param animation The {@link Animation} to add
     */
    public void addAnimation(Animation animation) {
        Objects.requireNonNull(animation, "The animation may not be null");
        animations.add(animation);
        updateMaxEndTime();
    }

    /**
     * Add all {@link Animation}s of the given sequence to this manager
     *
     * @param animations The {@link Animation}s to add
     */
    public void addAnimations(Iterable<? extends Animation> animations) {
        for (Animation animation : animations) {
            addAnimation(animation);
        }
    }


    /**
     * Remove the given {@link Animation} from this manager
     *
     * @param animation The {@link Animation} to remove
     */
    public void removeAnimation(Animation animation) {
        animations.remove(animation);
        updateMaxEndTime();
    }

    /**
     * Remove all {@link Animation}s of the given sequence from this manager
     *
     * @param animations The {@link Animation}s to remove
     */
    public void removeAnimations(Iterable<? extends Animation> animations) {
        for (Animation animation : animations) {
            removeAnimation(animation);
        }
    }

    /**
     * Returns an unmodifiable view on the animations that are stored
     * in this manager
     *
     * @return The animations
     */
    public List<Animation> getAnimations() {
        return Collections.unmodifiableList(animations);
    }

    /**
     * Update the {@link #maxEndTimeS}, the maximum end time of any
     * {@link Animation}
     */
    private void updateMaxEndTime() {
        maxEndTimeS = 0.0f;
        for (Animation animation : animations) {
            maxEndTimeS = Math.max(maxEndTimeS, animation.getEndTimeS());
        }
    }

    /**
     * Perform a time step, with the given size (in nanoseconds), and
     * update all {@link Animation}s
     *
     * @param deltaNs The time step size, in nanoseconds
     */
    public void performStep(long deltaNs) {
        currentNs += deltaNs;
        float currentTimeS = getCurrentTimeS();
        if (animationPolicy == AnimationPolicy.ONCE &&
                currentTimeS > maxEndTimeS) {
            animations.clear();
            return;
        }
        for (Animation animation : animations) {
            if (animationPolicy == AnimationPolicy.LOOP) {
                float loopTimeS = currentTimeS % maxEndTimeS;
                animation.update(loopTimeS);
            } else if (animationPolicy == AnimationPolicy.PING_PONG) {
                int interval = (int) (currentTimeS / maxEndTimeS);
                float loopTimeS = currentTimeS % maxEndTimeS;
                float pingPongTimeS = loopTimeS;
                if ((interval & 1) != 0) {
                    pingPongTimeS = maxEndTimeS - loopTimeS;
                }
                animation.update(pingPongTimeS);
            } else {
                animation.update(currentTimeS);
            }
        }
        fireAnimationsUpdated();
    }

    public void setTime(float currentTimeS) {
        for (Animation animation : animations) {
            if (animationPolicy == AnimationPolicy.LOOP) {
                float loopTimeS = currentTimeS % maxEndTimeS;
                animation.update(loopTimeS);
            } else if (animationPolicy == AnimationPolicy.PING_PONG) {
                int interval = (int) (currentTimeS / maxEndTimeS);
                float loopTimeS = currentTimeS % maxEndTimeS;
                float pingPongTimeS = loopTimeS;
                if ((interval & 1) != 0) {
                    pingPongTimeS = maxEndTimeS - loopTimeS;
                }
                animation.update(pingPongTimeS);
            } else {
                animation.update(currentTimeS);
            }
        }
    }

    /**
     * Add the given {@link AnimationManagerListener} to be informed about
     * changes in this manager
     *
     * @param listener The listener to add
     */
    public void addAnimationManagerListener(
            AnimationManagerListener listener) {
        animationManagerListeners.add(listener);
    }

    /**
     * Remove the given {@link AnimationManagerListener}
     *
     * @param listener The listener to remove
     */
    public void removeAnimationManagerListener(
            AnimationManagerListener listener) {
        animationManagerListeners.remove(listener);
    }


    /**
     * Inform all registered {@link AnimationManagerListener}s that
     * the animations have been updated
     */
    private void fireAnimationsUpdated() {
        for (AnimationManagerListener listener : animationManagerListeners) {
            listener.animationsUpdated(this);
        }
    }


}