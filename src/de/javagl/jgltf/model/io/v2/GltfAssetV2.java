package de.javagl.jgltf.model.io.v2;

import de.javagl.jgltf.impl.v2.Buffer;
import de.javagl.jgltf.impl.v2.GlTF;
import de.javagl.jgltf.impl.v2.Image;
import de.javagl.jgltf.model.Optionals;
import de.javagl.jgltf.model.io.Buffers;
import de.javagl.jgltf.model.io.GltfAsset;
import de.javagl.jgltf.model.io.GltfReference;
import de.javagl.jgltf.model.io.IO;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * Implementation of the {@link GltfAsset} interface for glTF 2.0.
 */
public final class GltfAssetV2 implements GltfAsset {
    /**
     * The {@link GlTF}
     */
    private final GlTF gltf;

    /**
     * The optional binary data
     */
    private final ByteBuffer binaryData;

    /**
     * The mapping from (relative) URI strings to the associated external data
     */
    private final Map<String, ByteBuffer> referenceDatas;

    /**
     * Creates a new instance
     *
     * @param gltf       The {@link GlTF}
     * @param binaryData The optional binary data
     */
    public GltfAssetV2(GlTF gltf, ByteBuffer binaryData) {
        this.gltf = Objects.requireNonNull(gltf, "The gltf may not be null");
        this.binaryData = binaryData;
        this.referenceDatas = new ConcurrentHashMap<>();
    }

    /**
     * Store the given byte buffer under the given (relative) URI string
     *
     * @param uriString  The URI string
     * @param byteBuffer The byte buffer
     */
    void putReferenceData(String uriString, ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            referenceDatas.remove(uriString);
        } else {
            referenceDatas.put(uriString, byteBuffer);
        }
    }

    @Override
    public GlTF getGltf() {
        return gltf;
    }

    @Override
    public ByteBuffer getBinaryData() {
        return Buffers.createSlice(binaryData);
    }

    @Override
    public List<GltfReference> getReferences() {
        List<GltfReference> references = new ArrayList<>();
        references.addAll(getBufferReferences());
        references.addAll(getImageReferences());
        return references;
    }

    /**
     * Create a list containing all {@link GltfReference} objects for the
     * buffers that are contained in this model.
     *
     * @return The references
     */
    public List<GltfReference> getBufferReferences() {
        List<GltfReference> references = new ArrayList<>();
        List<Buffer> buffers = Optionals.of(gltf.getBuffers());
        for (int i = 0; i < buffers.size(); i++) {
            Buffer buffer = buffers.get(i);
            if (buffer.getUri() == null) {
                // This is the binary glTF buffer
                continue;
            }
            String uri = buffer.getUri();
            if (!IO.isDataUriString(uri)) {
                Consumer<ByteBuffer> target =
                        byteBuffer -> putReferenceData(uri, byteBuffer);
                GltfReference reference =
                        new GltfReference("buffer " + i, uri, target);
                references.add(reference);
            }
        }
        return references;
    }

    /**
     * Create a list containing all {@link GltfReference} objects for the
     * images that are contained in this model.
     *
     * @return The references
     */
    public List<GltfReference> getImageReferences() {
        List<GltfReference> references = new ArrayList<>();
        List<Image> images = Optionals.of(gltf.getImages());
        for (int i = 0; i < images.size(); i++) {
            Image image = images.get(i);
            if (image.getBufferView() != null) {
                // This is an image that refers to a buffer view
                continue;
            }
            String uri = image.getUri();
            if (!IO.isDataUriString(uri)) {
                Consumer<ByteBuffer> target =
                        byteBuffer -> putReferenceData(uri, byteBuffer);
                GltfReference reference =
                        new GltfReference("image " + i, uri, target);
                references.add(reference);
            }
        }
        return references;
    }

    @Override
    public ByteBuffer getReferenceData(String uriString) {
        return Buffers.createSlice(referenceDatas.get(uriString));
    }

    @Override
    public Map<String, ByteBuffer> getReferenceDatas() {
        return Collections.unmodifiableMap(referenceDatas);
    }


}
