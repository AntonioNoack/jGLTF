package de.javagl.jgltf.model;

import java.util.List;

/**
 * Interface for an animation
 */
public interface AnimationModel extends NamedModelElement {

    /**
     * Enumeration of the different interpolation methods for an animation
     */
    public enum Interpolation {
        /**
         * Stepwise interpolation
         */
        STEP,

        /**
         * Linear interpolation
         */
        LINEAR,

        /**
         * Cubic spline interpolation
         */
        CUBICSPLINE
    }

    /**
     * Interface for an animation channel
     */
    public interface Channel {
        /**
         * Returns the {@link Sampler} for this channel
         *
         * @return The {@link Sampler}
         */
        Sampler getSampler();

        /**
         * Returns the optional {@link NodeModel} to which the animated
         * property (path) belongs.
         *
         * @return The {@link NodeModel}
         */
        NodeModel getNodeModel();

        /**
         * Returns the path describing the animated property
         *
         * @return The path
         */
        String getPath();
    }

    /**
     * Interface for an animation sampler
     */
    public interface Sampler {
        /**
         * Returns the {@link AccessorModel} that contains the input (time
         * key frame) data
         *
         * @return The input data
         */
        AccessorModel getInput();

        /**
         * Returns the {@link Interpolation} method
         *
         * @return The {@link Interpolation}
         */
        Interpolation getInterpolation();

        /**
         * Returns the {@link AccessorModel} that contains the output (value
         * key frame) data
         *
         * @return The output data
         */
        AccessorModel getOutput();
    }

    /**
     * Returns an unmodifiable list containing the {@link Channel} instances
     * of the animation
     *
     * @return The {@link Channel} instances
     */
    List<Channel> getChannels();
}
