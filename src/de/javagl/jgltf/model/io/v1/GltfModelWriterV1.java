package de.javagl.jgltf.model.io.v1;

import de.javagl.jgltf.impl.v1.GlTF;
import de.javagl.jgltf.model.io.GltfAssetWriter;
import de.javagl.jgltf.model.io.GltfModelWriter;
import de.javagl.jgltf.model.io.GltfWriter;
import de.javagl.jgltf.model.v1.GltfModelV1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A class for writing a {@link GltfModelV1}. This class contains
 * implementations for the methods of the {@link GltfModelWriter},
 * for glTF 1.0 assets. Clients should not use this class directly,
 * but only the {@link GltfModelWriter}.
 */
public final class GltfModelWriterV1 {
    /**
     * Default constructor
     */
    public GltfModelWriterV1() {
        // Default constructor
    }

    /**
     * Write the given {@link GltfModelV1} to the given file. External
     * references of buffers, images and shaders that are given via
     * the respective URI string will be resolved against the parent
     * directory of the given file, and the corresponding data will
     * be written into the corresponding files.
     *
     * @param gltfModel The {@link GltfModelV1}
     * @param file      The file
     * @throws IOException If an IO error occurs
     */
    public void write(GltfModelV1 gltfModel, File file)
            throws IOException {
        DefaultAssetCreatorV1 assetCreator = new DefaultAssetCreatorV1();
        GltfAssetV1 gltfAsset = assetCreator.create(gltfModel);
        GltfAssetWriter gltfAssetWriter = new GltfAssetWriter();
        gltfAssetWriter.write(gltfAsset, file);
    }

    /**
     * Write the given {@link GltfModelV1} as a binary glTF asset to the
     * given file
     *
     * @param gltfModel The {@link GltfModelV1}
     * @param file      The file
     * @throws IOException If an IO error occurs
     */
    public void writeBinary(GltfModelV1 gltfModel, File file)
            throws IOException {
        try (OutputStream outputStream = new FileOutputStream(file)) {
            writeBinary(gltfModel, outputStream);
        }
    }

    /**
     * Write the given {@link GltfModelV1} as a binary glTF asset to the
     * given output stream. The caller is responsible for closing the
     * given stream.
     *
     * @param gltfModel    The {@link GltfModelV1}
     * @param outputStream The output stream
     * @throws IOException If an IO error occurs
     */
    public void writeBinary(GltfModelV1 gltfModel, OutputStream outputStream)
            throws IOException {
        BinaryAssetCreatorV1 assetCreator = new BinaryAssetCreatorV1();
        GltfAssetV1 gltfAsset = assetCreator.create(gltfModel);
        GltfAssetWriterV1 gltfAssetWriter = new GltfAssetWriterV1();
        gltfAssetWriter.writeBinary(gltfAsset, outputStream);
    }

    /**
     * Write the given {@link GltfModelV1} as an embedded glTF asset to the
     * given output stream. The caller is responsible for closing the
     * given stream.
     *
     * @param gltfModel    The {@link GltfModelV1}
     * @param outputStream The output stream
     * @throws IOException If an IO error occurs
     */
    public void writeEmbedded(GltfModelV1 gltfModel, OutputStream outputStream)
            throws IOException {
        EmbeddedAssetCreatorV1 assetCreator = new EmbeddedAssetCreatorV1();
        GltfAssetV1 gltfAsset = assetCreator.create(gltfModel);
        GltfWriter gltfWriter = new GltfWriter();
        GlTF gltf = gltfAsset.getGltf();
        gltfWriter.write(gltf, outputStream);
    }
}
