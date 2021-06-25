package de.javagl.jgltf.model.io;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import de.javagl.jgltf.logging.Logger;

/**
 * A class for resolving the external data of {@link GltfReference} objects
 * that are obtained from a {@link GltfAsset}
 */
public class GltfReferenceResolver {
    /**
     * The logger used in this class
     */
    private static final Logger logger =
            Logger.getLogger(GltfReferenceResolver.class);

    /**
     * Calls {@link #resolve(GltfReference, Function)} with each
     * {@link GltfReference} of the given list, resolving the
     * URIs of the references against the given base URI
     *
     * @param references The {@link GltfReference} objects
     * @param baseUri    The base URI that references will be resolved against
     */
    public static void resolveAll(
            Iterable<? extends GltfReference> references, URI baseUri) {
        Objects.requireNonNull(references, "The references may not be null");
        Objects.requireNonNull(baseUri, "The baseUri may not be null");
        Function<String, ByteBuffer> uriResolver =
                UriResolvers.createBaseUriResolver(baseUri);
        resolveAll(references, uriResolver);
    }

    /**
     * Calls {@link #resolve(GltfReference, Function)} with each
     * {@link GltfReference} of the given list
     *
     * @param references  The {@link GltfReference} objects
     * @param uriResolver The function for resolving a URI string
     *                    into a byte buffer
     */
    public static void resolveAll(
            Iterable<? extends GltfReference> references,
            Function<? super String, ? extends ByteBuffer> uriResolver) {
        Objects.requireNonNull(references, "The references may not be null");
        Objects.requireNonNull(uriResolver, "The uriResolver may not be null");

        for (GltfReference reference : references) {
            resolve(reference, uriResolver);
        }
    }

    /**
     * Pass the {@link GltfReference#getUri() URI} of the given
     * {@link GltfReference} to the given resolver function,
     * and and pass the resulting byte buffer to the
     * {@link GltfReference#getTarget() target} of the reference. If
     * a URI cannot be resolved, a warning will be printed.
     *
     * @param reference   The {@link GltfReference}
     * @param uriResolver The function for resolving a URI string
     *                    into an byte buffer
     */
    public static void resolve(GltfReference reference,
                               Function<? super String, ? extends ByteBuffer> uriResolver) {
        Objects.requireNonNull(reference, "The reference may not be null");
        Objects.requireNonNull(uriResolver, "The uriResolver may not be null");

        String uri = reference.getUri();
        ByteBuffer byteBuffer = uriResolver.apply(uri);
        if (byteBuffer == null) {
            logger.warning("Could not resolve URI " + uri);
        }
        Consumer<ByteBuffer> target = reference.getTarget();
        target.accept(byteBuffer);
    }

    /**
     * Private constructor to prevent instantiation
     */
    private GltfReferenceResolver() {
        // Private constructor to prevent instantiation
    }

}
