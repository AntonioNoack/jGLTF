package de.javagl.jgltf.model.io.v2;

import de.javagl.jgltf.impl.v2.GlTF;
import de.javagl.jgltf.model.io.GltfAssetWriter;
import de.javagl.jgltf.model.io.GltfModelWriter;
import de.javagl.jgltf.model.io.GltfWriter;
import de.javagl.jgltf.model.v2.GltfModelV2;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A class for writing a {@link GltfModelV2}. This class contains
 * implementations for the methods of the {@link GltfModelWriter},
 * for glTF 2.0 assets. Clients should not use this class directly,
 * but only the {@link GltfModelWriter}.
 */
public final class GltfModelWriterV2 {
    /**
     * Default constructor
     */
    public GltfModelWriterV2() {
        // Default constructor
    }

    /**
     * Write the given {@link GltfModelV2} to the given file. External
     * references of buffers and images that are given via the respective
     * URI string will be resolved against the parent directory of the
     * given file, and the corresponding data will be written into
     * the corresponding files.
     *
     * @param gltfModel The {@link GltfModelV2}
     * @param file      The file
     * @throws IOException If an IO error occurs
     */
    public void write(GltfModelV2 gltfModel, File file)
            throws IOException {
        DefaultAssetCreatorV2 assetCreator = new DefaultAssetCreatorV2();
        GltfAssetV2 gltfAsset = assetCreator.create(gltfModel);
        GltfAssetWriter gltfAssetWriter = new GltfAssetWriter();
        gltfAssetWriter.write(gltfAsset, file);
    }

    /**
     * Write the given {@link GltfModelV2} as a binary glTF asset to the
     * given output stream. The caller is responsible for closing the
     * given stream.
     *
     * @param gltfModel    The {@link GltfModelV2}
     * @param outputStream The output stream
     * @throws IOException If an IO error occurs
     */
    public void writeBinary(GltfModelV2 gltfModel, OutputStream outputStream)
            throws IOException {
        BinaryAssetCreatorV2 assetCreator = new BinaryAssetCreatorV2();
        GltfAssetV2 gltfAsset = assetCreator.create(gltfModel);
        GltfAssetWriterV2 gltfAssetWriter = new GltfAssetWriterV2();
        gltfAssetWriter.writeBinary(gltfAsset, outputStream);
    }

    /**
     * Write the given {@link GltfModelV2} as an embedded glTF asset to the
     * given output stream. The caller is responsible for closing the
     * given stream.
     *
     * @param gltfModel    The {@link GltfModelV2}
     * @param outputStream The output stream
     * @throws IOException If an IO error occurs
     */
    public void writeEmbedded(GltfModelV2 gltfModel, OutputStream outputStream)
            throws IOException {
        EmbeddedAssetCreatorV2 assetCreator = new EmbeddedAssetCreatorV2();
        GltfAssetV2 gltfAsset = assetCreator.create(gltfModel);
        GltfWriter gltfWriter = new GltfWriter();
        GlTF gltf = gltfAsset.getGltf();
        gltfWriter.write(gltf, outputStream);
    }
}
