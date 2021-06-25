package de.javagl.jgltf.model.gl;

import de.javagl.jgltf.model.NamedModelElement;

import java.nio.ByteBuffer;

/**
 * Interface for a shader
 */
public interface ShaderModel extends NamedModelElement {
    /**
     * Enumeration of different shader types
     */
    public enum ShaderType {
        /**
         * The vertex shader type
         */
        VERTEX_SHADER,

        /**
         * The fragment shader type
         */
        FRAGMENT_SHADER
    }

    /**
     * Returns the URI of the shader data
     *
     * @return The URI
     */
    String getUri();

    /**
     * Returns the actual shader data. This will return a slice of the
     * buffer that is stored internally. Thus, changes to the contents
     * of this buffer will affect this model, but modifications of the
     * position and limit of the returned buffer will not affect this
     * model.<br>
     *
     * @return The shader data
     */
    ByteBuffer getShaderData();

    /**
     * Returns the shader source code as a string. This is a convenience
     * function that simply returns the {@link #getShaderData() shader data}
     * as a string.
     *
     * @return The shader source code
     */
    String getShaderSource();

    /**
     * Returns the {@link ShaderType} of this shader
     *
     * @return The {@link ShaderType}
     */
    ShaderType getShaderType();

}