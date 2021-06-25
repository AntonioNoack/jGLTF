package de.javagl.jgltf.model.impl;

import de.javagl.jgltf.model.AccessorModel;
import de.javagl.jgltf.model.AnimationModel;
import de.javagl.jgltf.model.NodeModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of an {@link AnimationModel}
 */
public class DefaultAnimationModel extends AbstractNamedModelElement
        implements AnimationModel {
    /**
     * Default implementation of a
     * {@link de.javagl.jgltf.model.AnimationModel.Sampler}
     */
    public static class DefaultSampler implements Sampler {
        /**
         * The input data
         */
        private final AccessorModel input;

        /**
         * The interpolation method
         */
        public final Interpolation interpolation;

        /**
         * The output data
         */
        private final AccessorModel output;

        /**
         * Default constructor
         *
         * @param input         The input
         * @param interpolation The interpolation
         * @param output        The output
         */
        public DefaultSampler(
                AccessorModel input,
                Interpolation interpolation,
                AccessorModel output) {
            this.input = Objects.requireNonNull(
                    input, "The input may not be null");
            this.interpolation = Objects.requireNonNull(
                    interpolation, "The interpolation may not be null");
            this.output = Objects.requireNonNull(
                    output, "The output may not be null");
        }

        @Override
        public AccessorModel getInput() {
            return input;
        }

        @Override
        public Interpolation getInterpolation() {
            return interpolation;
        }

        @Override
        public AccessorModel getOutput() {
            return output;
        }

        @Override
        public String toString() {
            return "DefaultSampler{" +
                    "interpolation=" + interpolation +
                    '}';
        }
    }

    /**
     * Default implementation of a
     * {@link de.javagl.jgltf.model.AnimationModel.Channel}
     */
    public static class DefaultChannel implements Channel {
        /**
         * The sampler
         */
        public final Sampler sampler;

        /**
         * The node model
         */
        public final NodeModel nodeModel;

        /**
         * The path
         */
        public final String path;

        /**
         * Default constructor
         *
         * @param sampler   The sampler
         * @param nodeModel The node model
         * @param path      The path
         */
        public DefaultChannel(
                Sampler sampler,
                NodeModel nodeModel,
                String path) {
            this.sampler = Objects.requireNonNull(
                    sampler, "The sampler may not be null");
            this.nodeModel = nodeModel;
            this.path = Objects.requireNonNull(
                    path, "The path may not be null");

        }

        @Override
        public Sampler getSampler() {
            return sampler;
        }

        @Override
        public NodeModel getNodeModel() {
            return nodeModel;
        }

        @Override
        public String getPath() {
            return path;
        }

        @Override
        public String toString() {
            return "DefaultChannel{" +
                    "sampler=" + sampler +
                    ", nodeModel=" + nodeModel +
                    ", path='" + path + '\'' +
                    '}';
        }
    }

    /**
     * The {@link de.javagl.jgltf.model.AnimationModel.Channel} instances
     * of this animation
     */
    public final List<Channel> channels;

    /**
     * Creates a new instance
     */
    public DefaultAnimationModel() {
        this.channels = new ArrayList<>();
    }

    /**
     * Add the given {@link de.javagl.jgltf.model.AnimationModel.Channel}
     *
     * @param channel The {@link de.javagl.jgltf.model.AnimationModel.Channel}
     */
    public void addChannel(Channel channel) {
        Objects.requireNonNull(channel, "The channel may not be null");
        this.channels.add(channel);
    }

    @Override
    public List<Channel> getChannels() {
        return Collections.unmodifiableList(channels);
    }

    @Override
    public String toString() {
        return "DefaultAnimationModel{" +
                "name=" + getName() +
                ", channels=" + channels +
                '}';
    }
}