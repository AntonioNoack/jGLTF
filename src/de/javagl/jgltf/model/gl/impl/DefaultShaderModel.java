package de.javagl.jgltf.model.gl.impl;

import de.javagl.jgltf.model.gl.ShaderModel;
import de.javagl.jgltf.model.impl.AbstractNamedModelElement;
import de.javagl.jgltf.model.io.Buffers;

import java.nio.ByteBuffer;

/**
 * Implementation of a {@link ShaderModel}
 */
public class DefaultShaderModel extends AbstractNamedModelElement
        implements ShaderModel {
    /**
     * The URI
     */
    private final String uri;

    /**
     * The actual raw shader data
     */
    private ByteBuffer shaderData;

    /**
     * The {@link de.javagl.jgltf.model.gl.ShaderModel.ShaderType}
     */
    private final ShaderType shaderType;

    /**
     * Default constructor
     *
     * @param uri        The URI
     * @param shaderType The
     *                   {@link de.javagl.jgltf.model.gl.ShaderModel.ShaderType}
     */
    public DefaultShaderModel(String uri, ShaderType shaderType) {
        this.uri = uri;
        this.shaderType = shaderType;
    }

    /**
     * Set the data of this shader
     *
     * @param shaderData The shader data
     */
    public void setShaderData(ByteBuffer shaderData) {
        this.shaderData = shaderData;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public ByteBuffer getShaderData() {
        return Buffers.createSlice(shaderData);
    }

    @Override
    public String getShaderSource() {
        return Buffers.readAsString(shaderData);
    }

    @Override
    public ShaderType getShaderType() {
        return shaderType;
    }
}
