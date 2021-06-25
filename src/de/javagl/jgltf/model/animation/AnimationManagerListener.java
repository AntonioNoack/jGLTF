package de.javagl.jgltf.model.animation;

/**
 * Interface for classes that want to be informed about changes
 * in an {@link AnimationManager}
 */
public interface AnimationManagerListener {
    /**
     * Will be called when the {@link Animation}s in the given
     * {@link AnimationManager} have been updated
     *
     * @param source The {@link AnimationManager}
     */
    void animationsUpdated(AnimationManager source);
}