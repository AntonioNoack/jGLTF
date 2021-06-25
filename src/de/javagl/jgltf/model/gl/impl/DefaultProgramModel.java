package de.javagl.jgltf.model.gl.impl;

import de.javagl.jgltf.model.gl.ProgramModel;
import de.javagl.jgltf.model.gl.ShaderModel;
import de.javagl.jgltf.model.impl.AbstractNamedModelElement;

import java.util.Objects;

/**
 * Implementation of a {@link ProgramModel}
 */
public class DefaultProgramModel extends AbstractNamedModelElement
        implements ProgramModel {
    /**
     * The vertex shader model
     */
    private ShaderModel vertexShaderModel;

    /**
     * The fragment shader model
     */
    private ShaderModel fragmentShaderModel;

    /**
     * Default constructor
     */
    public DefaultProgramModel() {
        // Default constructor
    }

    /**
     * Set the vertex {@link ShaderModel}
     *
     * @param vertexShaderModel The vertex {@link ShaderModel}
     */
    public void setVertexShaderModel(ShaderModel vertexShaderModel) {
        this.vertexShaderModel = Objects.requireNonNull(vertexShaderModel,
                "The vertexShaderModel may not be null");
    }

    @Override
    public ShaderModel getVertexShaderModel() {
        return vertexShaderModel;
    }

    /**
     * Set the fragment {@link ShaderModel}
     *
     * @param fragmentShaderModel The fragment {@link ShaderModel}
     */
    public void setFragmentShaderModel(ShaderModel fragmentShaderModel) {
        this.fragmentShaderModel = Objects.requireNonNull(fragmentShaderModel,
                "The fragmentShaderModel may not be null");
    }

    @Override
    public ShaderModel getFragmentShaderModel() {
        return fragmentShaderModel;
    }
}

