package de.javagl.jgltf.model.v1.gl;

import de.javagl.jgltf.impl.v1.Material;
import de.javagl.jgltf.impl.v1.Technique;

import java.util.Arrays;

/**
 * Utility methods for {@link Material}s
 */
class Materials {
    /**
     * Create a default {@link Material} with the given {@link Technique} ID,
     * that is assumed to refer to a {@link Techniques#createDefaultTechnique(
     *String) default technique}.<br>
     * <br>
     * The returned {@link Material} is the default {@link Material}, as
     * described in "Appendix A" of the glTF 1.0 specification.
     *
     * @param techniqueId The {@link Technique} ID
     * @return The default {@link Material}
     */
    static Material createDefaultMaterial(String techniqueId) {
        Material material = new Material();
        material.addValues("emission", Arrays.asList(0.5, 0.5, 0.5, 1.0));
        material.setTechnique(techniqueId);
        return material;
    }

    /**
     * Private constructor to prevent instantiation
     */
    private Materials() {
        // Private constructor to prevent instantiation
    }

}
