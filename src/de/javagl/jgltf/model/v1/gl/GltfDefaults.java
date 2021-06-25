package de.javagl.jgltf.model.v1.gl;

import de.javagl.jgltf.impl.v1.Material;
import de.javagl.jgltf.impl.v1.Program;
import de.javagl.jgltf.impl.v1.Shader;
import de.javagl.jgltf.impl.v1.Technique;

/**
 * A class containing the default {@link Shader}, {@link Program},
 * {@link Technique} and {@link Material} objects according to
 * "Appendix A" of the specification.
 */
public class GltfDefaults {
    /**
     * An ID for the default vertex {@link Shader}
     */
    private static final String DEFAULT_VERTEX_SHADER_ID =
            GltfDefaults.class.getName() + ".DEFAULT_VERTEX_SHADER_ID";

    /**
     * An ID for the default fragment {@link Shader}
     */
    private static final String DEFAULT_FRAGMENT_SHADER_ID =
            GltfDefaults.class.getName() + ".DEFAULT_FRAGMENT_SHADER_ID";

    /**
     * An ID for the default {@link Program}
     */
    private static final String DEFAULT_PROGRAM_ID =
            GltfDefaults.class.getName() + ".DEFAULT_PROGRAM_ID";

    /**
     * An ID for the default {@link Technique}
     */
    private static final String DEFAULT_TECHNIQUE_ID =
            GltfDefaults.class.getName() + ".DEFAULT_TECHNIQUE_ID";

    /**
     * An ID for the default {@link Material}
     */
    private static final String DEFAULT_MATERIAL_ID =
            GltfDefaults.class.getName() + ".DEFAULT_MATERIAL_ID";

    /**
     * The default vertex {@link Shader}
     */
    private static final Shader DEFAULT_VERTEX_SHADER =
            Shaders.createDefaultVertexShader();

    /**
     * The default fragment {@link Shader}
     */
    private static final Shader DEFAULT_FRAGMENT_SHADER =
            Shaders.createDefaultFragmentShader();

    /**
     * The default {@link Program}
     */
    private static final Program DEFAULT_PROGRAM =
            Programs.createDefaultProgram(
                    DEFAULT_VERTEX_SHADER_ID, DEFAULT_FRAGMENT_SHADER_ID);

    /**
     * The default {@link Technique}
     */
    private static final Technique DEFAULT_TECHNIQUE =
            Techniques.createDefaultTechnique(DEFAULT_PROGRAM_ID);

    /**
     * The default {@link Material}
     */
    private static final Material DEFAULT_MATERIAL =
            Materials.createDefaultMaterial(DEFAULT_TECHNIQUE_ID);

    /**
     * Returns whether the given ID is the default ID as defined in this class
     *
     * @param id The ID
     * @return Whether the given ID is the default ID
     */
    public static boolean isDefaultTechniqueId(String id) {
        return DEFAULT_TECHNIQUE_ID.equals(id);
    }

    /**
     * Returns whether the given ID is the default ID as defined in this class
     *
     * @param id The ID
     * @return Whether the given ID is the default ID
     */
    public static boolean isDefaultMaterialId(String id) {
        return DEFAULT_MATERIAL_ID.equals(id);
    }

    /**
     * Returns the default vertex {@link Shader}
     *
     * @return The {@link Shader}
     */
    static Shader getDefaultVertexShader() {
        return DEFAULT_VERTEX_SHADER;
    }

    /**
     * Returns the default fragment {@link Shader}
     *
     * @return The {@link Shader}
     */
    static Shader getDefaultFragmentShader() {
        return DEFAULT_FRAGMENT_SHADER;
    }

    /**
     * Returns the default {@link Technique}
     *
     * @return The {@link Technique}
     */
    static Technique getDefaultTechnique() {
        return DEFAULT_TECHNIQUE;
    }

    /**
     * Returns the default {@link Material}
     *
     * @return The {@link Material}
     */
    static Material getDefaultMaterial() {
        return DEFAULT_MATERIAL;
    }

    /**
     * Private constructor to prevent instantiation
     */
    private GltfDefaults() {
        // Private constructor to prevent instantiation
    }

}


