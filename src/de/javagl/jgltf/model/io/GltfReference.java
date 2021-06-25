package de.javagl.jgltf.model.io;

import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * A reference to an external resource that belongs to a {@link GltfAsset}.
 */
public final class GltfReference {
    /**
     * The name of the external resource
     */
    private final String name;

    /**
     * The (relative) URI of the reference
     */
    private final String uri;

    /**
     * The target that is supposed to receive the binary data that was
     * read from the external resource
     */
    private final Consumer<ByteBuffer> target;

    /**
     * Default constructor
     *
     * @param name   The name of the external resource
     * @param uri    The (relative) URI of the reference
     * @param target The target that is supposed to receive the binary
     *               data that was read from the external resource
     */
    public GltfReference(String name, String uri, Consumer<ByteBuffer> target) {
        this.name = Objects.requireNonNull(
                name, "The name may not be null");
        this.uri = Objects.requireNonNull(
                uri, "The uri may not be null");
        this.target = Objects.requireNonNull(
                target, "The target may not be null");
    }

    /**
     * Returns the name of the external resource
     *
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the (relative) URI of the reference
     *
     * @return The URI
     */
    public String getUri() {
        return uri;
    }

    /**
     * Returns the target that is supposed to receive the binary
     * data that was read from the external resource
     *
     * @return The target
     */
    public Consumer<ByteBuffer> getTarget() {
        return target;
    }
}
