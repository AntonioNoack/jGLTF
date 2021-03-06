package de.javagl.jgltf.model.v2;

import de.javagl.jgltf.impl.v2.Material;
import de.javagl.jgltf.impl.v2.MaterialPbrMetallicRoughness;
import de.javagl.jgltf.impl.v2.TextureInfo;
import de.javagl.jgltf.model.Optionals;
import de.javagl.jgltf.model.v2.gl.Materials;

import java.util.Objects;

/**
 * #ANNO no reason to have private stuff, and code things twice
 * A simple (package-private!) class describing the structure of a material.
 * It is mainly used as a key for map lookups, to decide whether for a
 * certain structure of a material a new technique is required.
 */
public class MaterialStructure {
    /**
     * The base color texture coordinates semantic string
     */
    private final String baseColorTexCoordSemantic;

    /**
     * The metallic-roughness texture coordinates semantic string
     */
    private final String metallicRoughnessTexCoordSemantic;

    /**
     * The normal texture coordinates semantic string
     */
    private final String normalTexCoordSemantic;

    /**
     * The occlusion texture coordinates semantic string
     */
    private final String occlusionTexCoordSemantic;

    /**
     * The emissive texture coordinates semantic string
     */
    private final String emissiveTexCoordSemantic;

    /**
     * The number of joints
     */
    private final int numJoints;

    /**
     * Default constructor
     *
     * @param material  The {@link Material}
     * @param numJoints The number of joints
     */
    MaterialStructure(Material material, int numJoints) {
        MaterialPbrMetallicRoughness pbrMetallicRoughness =
                material.getPbrMetallicRoughness();
        if (pbrMetallicRoughness == null) {
            pbrMetallicRoughness =
                    Materials.createDefaultMaterialPbrMetallicRoughness();
        }

        this.baseColorTexCoordSemantic =
                getTexCoordSemantic(pbrMetallicRoughness.getBaseColorTexture());
        this.metallicRoughnessTexCoordSemantic =
                getTexCoordSemantic(
                        pbrMetallicRoughness.getMetallicRoughnessTexture());
        this.normalTexCoordSemantic =
                getTexCoordSemantic(material.getNormalTexture());
        this.occlusionTexCoordSemantic =
                getTexCoordSemantic(material.getOcclusionTexture());
        this.emissiveTexCoordSemantic =
                getTexCoordSemantic(material.getEmissiveTexture());

        this.numJoints = numJoints;
    }

    /**
     * Obtain the <code>TEXCOORD_n</code> semantic string based on the given
     * texture info, defaulting to <code>TEXCOORD_0</code>
     *
     * @param textureInfo The optional texture info
     * @return The string
     */
    private static String getTexCoordSemantic(TextureInfo textureInfo) {
        if (textureInfo == null) {
            return "TEXCOORD_0";
        }
        int texCoord = Optionals.of(
                textureInfo.getTexCoord(),
                textureInfo.defaultTexCoord());
        return "TEXCOORD_" + texCoord;
    }

    /**
     * Returns the texture coordinates semantic string
     *
     * @return The semantic string
     */
    String getBaseColorTexCoordSemantic() {
        return baseColorTexCoordSemantic;
    }

    /**
     * Returns the texture coordinates semantic string
     *
     * @return The semantic string
     */
    String getMetallicRoughnessTexCoordSemantic() {
        return metallicRoughnessTexCoordSemantic;
    }

    /**
     * Returns the texture coordinates semantic string
     *
     * @return The semantic string
     */
    String getNormalTexCoordSemantic() {
        return normalTexCoordSemantic;
    }

    /**
     * Returns the texture coordinates semantic string
     *
     * @return The semantic string
     */
    String getOcclusionTexCoordSemantic() {
        return occlusionTexCoordSemantic;
    }

    /**
     * Returns the texture coordinates semantic string
     *
     * @return The semantic string
     */
    String getEmissiveTexCoordSemantic() {
        return emissiveTexCoordSemantic;
    }

    /**
     * Returns the number of joints
     *
     * @return The number of joints
     */
    int getNumJoints() {
        return numJoints;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                baseColorTexCoordSemantic,
                metallicRoughnessTexCoordSemantic,
                normalTexCoordSemantic,
                occlusionTexCoordSemantic,
                emissiveTexCoordSemantic,
                numJoints);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }
        MaterialStructure other = (MaterialStructure) object;
        if (!Objects.equals(baseColorTexCoordSemantic,
                other.baseColorTexCoordSemantic)) {
            return false;
        }
        if (!Objects.equals(metallicRoughnessTexCoordSemantic,
                other.metallicRoughnessTexCoordSemantic)) {
            return false;
        }
        if (!Objects.equals(normalTexCoordSemantic,
                other.normalTexCoordSemantic)) {
            return false;
        }
        if (!Objects.equals(occlusionTexCoordSemantic,
                other.occlusionTexCoordSemantic)) {
            return false;
        }
        if (!Objects.equals(emissiveTexCoordSemantic,
                other.emissiveTexCoordSemantic)) {
            return false;
        }
        if (numJoints != other.numJoints) {
            return false;
        }
        return true;
    }


}