package de.javagl.jgltf.model;

import de.javagl.jgltf.model.io.GltfAsset;
import de.javagl.jgltf.model.io.v1.GltfAssetV1;
import de.javagl.jgltf.model.io.v2.GltfAssetV2;
import de.javagl.jgltf.model.v1.GltfModelV1;
import de.javagl.jgltf.model.v2.GltfModelV2;

/**
 * Methods to create {@link GltfModel} instances from a {@link GltfAsset}
 */
public class GltfModels {
    /**
     * Creates a {@link GltfModel} instance from the given {@link GltfAsset}
     *
     * @param gltfAsset The {@link GltfAsset}
     * @return The {@link GltfModel}
     * @throws IllegalArgumentException If the given asset has an
     *                                  unknown version
     */
    public static GltfModel create(GltfAsset gltfAsset) {
        if (gltfAsset instanceof GltfAssetV1) {
            GltfAssetV1 gltfAssetV1 = (GltfAssetV1) gltfAsset;
            return new GltfModelV1(gltfAssetV1);
        }
        if (gltfAsset instanceof GltfAssetV2) {
            GltfAssetV2 gltfAssetV2 = (GltfAssetV2) gltfAsset;
            return new GltfModelV2(gltfAssetV2);
        }
        throw new IllegalArgumentException(
                "The glTF asset has an unknown version: " + gltfAsset);
    }


    /**
     * Private constructor to prevent instantiation
     */
    private GltfModels() {
        // Private constructor to prevent instantiation
    }
}
