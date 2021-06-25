package de.javagl.jgltf.model.gl.impl;

import de.javagl.jgltf.model.gl.ProgramModel;
import de.javagl.jgltf.model.gl.TechniqueModel;
import de.javagl.jgltf.model.gl.TechniqueParametersModel;
import de.javagl.jgltf.model.gl.TechniqueStatesModel;
import de.javagl.jgltf.model.impl.AbstractNamedModelElement;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of a {@link TechniqueModel}
 */
public class DefaultTechniqueModel extends AbstractNamedModelElement
        implements TechniqueModel {
    /**
     * The {@link ProgramModel}
     */
    private ProgramModel programModel;

    /**
     * The parameters
     */
    private final Map<String, TechniqueParametersModel> parameters;

    /**
     * The attributes
     */
    private final Map<String, String> attributes;

    /**
     * The uniforms
     */
    private final Map<String, String> uniforms;

    /**
     * The {@link TechniqueStatesModel}
     */
    private TechniqueStatesModel techniqueStatesModel;

    /**
     * Default constructor
     */
    public DefaultTechniqueModel() {
        this.parameters = new LinkedHashMap<>();
        this.attributes = new LinkedHashMap<>();
        this.uniforms = new LinkedHashMap<>();
    }

    /**
     * Set the {@link ProgramModel}
     *
     * @param programModel The {@link ProgramModel}
     */
    public void setProgramModel(ProgramModel programModel) {
        this.programModel = programModel;
    }

    @Override
    public ProgramModel getProgramModel() {
        return programModel;
    }

    @Override
    public Map<String, TechniqueParametersModel> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    /**
     * Add the given attribute to this technique
     *
     * @param attributeName The attribute name
     * @param parameterName The parameter name
     */
    public void addAttribute(String attributeName, String parameterName) {
        Objects.requireNonNull(
                attributeName, "The attributeName may not be null");
        Objects.requireNonNull(
                parameterName, "The parameterName may not be null");
        attributes.put(attributeName, parameterName);
    }

    /**
     * Add the given parameter to this technique
     *
     * @param parameterName            The parameter name
     * @param techniqueParametersModel The {@link TechniqueParametersModel}
     */
    public void addParameter(
            String parameterName, TechniqueParametersModel techniqueParametersModel) {
        Objects.requireNonNull(
                parameterName, "The parameterName may not be null");
        Objects.requireNonNull(techniqueParametersModel,
                "The techniqueParametersModel may not be null");
        parameters.put(parameterName, techniqueParametersModel);
    }

    @Override
    public Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    @Override
    public TechniqueParametersModel getAttributeParameters(String attributeName) {
        String parameterName = attributes.get(attributeName);
        return parameters.get(parameterName);
    }

    /**
     * Add the given uniform to this technique
     *
     * @param uniformName   The uniform name
     * @param parameterName The parameter name
     */
    public void addUniform(String uniformName, String parameterName) {
        Objects.requireNonNull(
                uniformName, "The uniformName may not be null");
        Objects.requireNonNull(
                parameterName, "The parameterName may not be null");
        uniforms.put(uniformName, parameterName);
    }

    @Override
    public Map<String, String> getUniforms() {
        return Collections.unmodifiableMap(uniforms);
    }

    @Override
    public TechniqueParametersModel getUniformParameters(String uniformName) {
        String parameterName = uniforms.get(uniformName);
        return parameters.get(parameterName);
    }

    /**
     * Set the {@link TechniqueStatesModel}
     *
     * @param techniqueStatesModel The {@link TechniqueStatesModel}
     */
    public void setTechniqueStatesModel(
            TechniqueStatesModel techniqueStatesModel) {
        this.techniqueStatesModel = techniqueStatesModel;
    }

    @Override
    public TechniqueStatesModel getTechniqueStatesModel() {
        return techniqueStatesModel;
    }
}

