package de.javagl.jgltf.model.gl;

import de.javagl.jgltf.model.NamedModelElement;

/**
 * Interface for a program that consists of a vertex- and fragment
 * {@link ShaderModel}
 */
public interface ProgramModel extends NamedModelElement {
    /**
     * Return the {@link ShaderModel} for the vertex shader
     *
     * @return The {@link ShaderModel}
     */
    ShaderModel getVertexShaderModel();

    /**
     * Return the {@link ShaderModel} for the fragment shader
     *
     * @return The {@link ShaderModel}
     */
    ShaderModel getFragmentShaderModel();
}

