package de.javagl.jgltf.model.io;

import de.javagl.jgltf.model.GltfModel;
import de.javagl.jgltf.model.io.v1.GltfAssetV1;
import de.javagl.jgltf.model.io.v2.GltfAssetV2;
import de.javagl.jgltf.model.v1.GltfModelV1;
import de.javagl.jgltf.model.v2.GltfModelV2;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.function.Consumer;

/**
 * A class for reading a {@link GltfModel} from a URI.
 */
public final class GltfModelReader {
    /**
     * The consumer for {@link JsonError} instances
     */
    private Consumer<? super JsonError> jsonErrorConsumer =
            JsonErrorConsumers.createLogging();

    /**
     * Default constructor
     */
    public GltfModelReader() {
        // Default constructor
    }

    /**
     * Set the given consumer to receive {@link JsonError}s that may
     * occur when a glTF is read
     *
     * @param jsonErrorConsumer The {@link JsonError} consumer
     */
    public void setJsonErrorConsumer(
            Consumer<? super JsonError> jsonErrorConsumer) {
        this.jsonErrorConsumer = jsonErrorConsumer;
    }

    /**
     * Read the {@link GltfModel} from the given URI
     *
     * @param uri The URI
     * @return The {@link GltfModel}
     * @throws IOException If an IO error occurs
     */
    public GltfModel read(URI uri) throws IOException {
        GltfAssetReader gltfAssetReader = new GltfAssetReader();
        gltfAssetReader.setJsonErrorConsumer(jsonErrorConsumer);
        GltfAsset gltfAsset = gltfAssetReader.read(uri);
        return createModel(gltfAsset);
    }

    /**
     * Read the {@link GltfModel} from the given URI. In contrast to the
     * {@link #read(URI)} method, this method will not resolve any
     * references that are contained in the {@link GltfModel}. <br>
     * <br>
     * This is mainly intended for binary- or embedded glTF assets that do not
     * have external references.
     *
     * @param uri The URI
     * @return The {@link GltfModel}
     * @throws IOException If an IO error occurs
     */
    public GltfModel readWithoutReferences(URI uri) throws IOException {
        try (InputStream inputStream = uri.toURL().openStream()) {
            return readWithoutReferences(inputStream);
        }
    }

    /**
     * Read the {@link GltfModel} from the given input stream. In contrast
     * to the {@link #read(URI)} method, this method will not resolve any
     * references that are contained in the {@link GltfAsset}. <br>
     * <br>
     * This is mainly intended for binary- or embedded glTF assets that do not
     * have external references.
     *
     * @param inputStream The input stream to read from
     * @return The {@link GltfModel}
     * @throws IOException If an IO error occurs
     */
    public GltfModel readWithoutReferences(InputStream inputStream)
            throws IOException {
        GltfAssetReader gltfAssetReader = new GltfAssetReader();
        gltfAssetReader.setJsonErrorConsumer(jsonErrorConsumer);
        GltfAsset gltfAsset =
                gltfAssetReader.readWithoutReferences(inputStream);
        return createModel(gltfAsset);
    }

    /**
     * Creates a {@link GltfModel} instance from the given {@link GltfAsset}
     *
     * @param gltfAsset The {@link GltfAsset}
     * @return The {@link GltfModel}
     * @throws IOException If the given asset has an unknown version
     */
    private static GltfModel createModel(GltfAsset gltfAsset) throws IOException {
        if (gltfAsset instanceof GltfAssetV1) {
            GltfAssetV1 gltfAssetV1 = (GltfAssetV1) gltfAsset;
            return new GltfModelV1(gltfAssetV1);
        }
        if (gltfAsset instanceof GltfAssetV2) {
            GltfAssetV2 gltfAssetV2 = (GltfAssetV2) gltfAsset;
            return new GltfModelV2(gltfAssetV2);
        }
        throw new IOException(
                "The glTF asset has an unknown version: " + gltfAsset);
    }

}
