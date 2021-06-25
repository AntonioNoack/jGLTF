package de.javagl.jgltf.model.v1.gl;

import de.javagl.jgltf.impl.v1.Material;
import de.javagl.jgltf.impl.v1.Program;
import de.javagl.jgltf.impl.v1.Shader;

/**
 * Utility methods for {@link Program}s
 */
class Programs {
    /**
     * Creates a default {@link Program} with the given vertex- and
     * fragment {@link Shader} IDs, which are assumed to refer to
     * the {@link Shaders#createDefaultVertexShader() default vertex shader}
     * and {@link Shaders#createDefaultFragmentShader() default fragment
     * shader}.<br>
     * <br>
     * The returned {@link Program} is the {@link Program} for the default
     * {@link Material}, as described in "Appendix A" of the
     * glTF 1.0 specification.
     *
     * @param vertexShaderId   The vertex {@link Shader} ID
     * @param fragmentShaderId The fragment {@link Shader} ID
     * @return The default {@link Program}
     */
    static Program createDefaultProgram(
            String vertexShaderId, String fragmentShaderId) {
        Program program = new Program();
        program.setVertexShader(vertexShaderId);
        program.setFragmentShader(fragmentShaderId);
        program.addAttributes("a_position");
        return program;
    }

    /**
     * Private constructor to prevent instantiation
     */
    private Programs() {
        // Private constructor to prevent instantiation
    }

}
