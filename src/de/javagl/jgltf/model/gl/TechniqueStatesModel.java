package de.javagl.jgltf.model.gl;

import de.javagl.jgltf.model.GltfConstants;

import java.util.Arrays;
import java.util.List;

/**
 * Interface for technique states.
 */
public interface TechniqueStatesModel {
    /**
     * Returns an unmodifiable list containing the enabled states
     *
     * @return The enabled states
     */
    List<Integer> getEnable();

    /**
     * Returns the {@link TechniqueStatesFunctionsModel}
     *
     * @return The {@link TechniqueStatesFunctionsModel}
     */
    TechniqueStatesFunctionsModel getTechniqueStatesFunctionsModel();


    /**
     * Returns a list containing all possible states that may be contained
     * in a <code>technique.states.enable</code> list.
     *
     * @return All possible states
     */
    public static List<Integer> getAllStates() {
        return Arrays.asList(
                GltfConstants.GL_BLEND,
                GltfConstants.GL_CULL_FACE,
                GltfConstants.GL_DEPTH_TEST,
                GltfConstants.GL_POLYGON_OFFSET_FILL,
                GltfConstants.GL_SAMPLE_ALPHA_TO_COVERAGE,
                GltfConstants.GL_SCISSOR_TEST
        );
    }
}
