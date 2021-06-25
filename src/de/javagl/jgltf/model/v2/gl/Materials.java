package de.javagl.jgltf.model.v2.gl;

import de.javagl.jgltf.impl.v2.Material;
import de.javagl.jgltf.impl.v2.MaterialPbrMetallicRoughness;

/**
 * Methods to create instances of classes related to a {@link Material}
 */
public class Materials {
    /**
     * Create a {@link Material} with all default values
     *
     * @return The {@link Material}
     */
    public static Material createDefaultMaterial() {
        Material material = new Material();
        material.setPbrMetallicRoughness(
                createDefaultMaterialPbrMetallicRoughness());
        material.setNormalTexture(null);
        material.setOcclusionTexture(null);
        material.setEmissiveTexture(null);
        material.setEmissiveFactor(material.defaultEmissiveFactor());
        material.setAlphaMode(material.defaultAlphaMode());
        material.setAlphaCutoff(material.defaultAlphaCutoff());
        material.setDoubleSided(material.defaultDoubleSided());
        return material;
    }

    /**
     * Create a {@link MaterialPbrMetallicRoughness} with all default values
     *
     * @return The {@link MaterialPbrMetallicRoughness}
     */
    public static MaterialPbrMetallicRoughness
    createDefaultMaterialPbrMetallicRoughness() {
        MaterialPbrMetallicRoughness result =
                new MaterialPbrMetallicRoughness();
        result.setBaseColorFactor(result.defaultBaseColorFactor());
        result.setMetallicFactor(result.defaultMetallicFactor());
        result.setRoughnessFactor(result.defaultRoughnessFactor());
        return result;
    }

    /**
     * Private constructor to prevent instantiation
     */
    private Materials() {
        // Private constructor to prevent instantiation
    }
}
