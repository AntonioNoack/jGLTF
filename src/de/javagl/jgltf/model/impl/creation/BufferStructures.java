package de.javagl.jgltf.model.impl.creation;

import de.javagl.jgltf.model.BufferModel;
import de.javagl.jgltf.model.io.GltfReference;

import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import de.javagl.jgltf.logging.Logger;

/**
 * Utility methods related to {@link BufferStructure} instances.
 * Only intended for internal use.
 */
public class BufferStructures {
    /**
     * The logger used in this class
     */
    private static final Logger logger =
            Logger.getLogger(BufferStructures.class);

    /**
     * Resolve the given {@link GltfReference} instances against the given
     * {@link BufferStructure}. This will look up the {@link BufferModel}
     * instances in the given structure, based on the
     * {@link GltfReference#getUri() reference URI}, and pass the buffer
     * data to the {@link GltfReference#getTarget() reference target}.
     * If there is no {@link BufferModel} for a URI, then a warning will
     * be printed.
     *
     * @param bufferReferences The {@link GltfReference} instances
     * @param bufferStructure  The {@link BufferStructure}
     */
    public static void resolve(
            Iterable<? extends GltfReference> bufferReferences,
            BufferStructure bufferStructure) {
        List<BufferModel> bufferModels = bufferStructure.getBufferModels();
        Map<String, BufferModel> uriToBufferModel =
                new LinkedHashMap<>();
        for (BufferModel bufferModel : bufferModels) {
            String uri = bufferModel.getUri();
            uriToBufferModel.put(uri, bufferModel);
        }
        for (GltfReference bufferReference : bufferReferences) {
            String uri = bufferReference.getUri();
            BufferModel bufferModel = uriToBufferModel.get(uri);
            if (bufferModel == null) {
                logger.warning("Could not resolve buffer model for URI " + uri
                        + " in buffer structure");
            } else {
                Consumer<ByteBuffer> target = bufferReference.getTarget();
                target.accept(bufferModel.getBufferData());
            }
        }
    }

    /**
     * Private constructor to prevent instantiation
     */
    private BufferStructures() {
        // Private constructor to prevent instantiation
    }
}
