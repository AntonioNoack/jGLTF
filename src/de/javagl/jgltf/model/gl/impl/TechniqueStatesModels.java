package de.javagl.jgltf.model.gl.impl;

import de.javagl.jgltf.impl.v1.TechniqueStatesFunctions;
import de.javagl.jgltf.model.GltfConstants;
import de.javagl.jgltf.model.gl.TechniqueStatesFunctionsModel;
import de.javagl.jgltf.model.gl.TechniqueStatesModel;
import de.javagl.jgltf.model.gl.impl.v1.DefaultTechniqueStatesFunctionsModelV1;

import java.util.Arrays;
import java.util.List;

/**
 * Methods to create {@link TechniqueStatesModel} instances
 */
public class TechniqueStatesModels {
    /**
     * Create a default {@link TechniqueStatesModel}
     *
     * @return The {@link TechniqueStatesModel}
     */
    public static TechniqueStatesModel createDefault() {
        // This implementation is backed by the V1 implementation of the
        // technique states functions, but this will not be visible for
        // the caller
        List<Integer> enable = Arrays.asList(
                GltfConstants.GL_DEPTH_TEST,
                GltfConstants.GL_CULL_FACE
        );
        TechniqueStatesFunctions functions =
                de.javagl.jgltf.model.v1.gl.Techniques
                        .createDefaultTechniqueStatesFunctions();
        TechniqueStatesFunctionsModel techniqueStatesFunctionsModel =
                new DefaultTechniqueStatesFunctionsModelV1(functions);
        return new DefaultTechniqueStatesModel(
                enable, techniqueStatesFunctionsModel);
    }

    /**
     * Private constructor to prevent instantiation
     */
    private TechniqueStatesModels() {
        // Private constructor to prevent instantiation
    }
}
