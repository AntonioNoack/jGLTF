package de.javagl.jgltf.model.gl;

import de.javagl.jgltf.model.NamedModelElement;

import java.util.Map;

/**
 * Interface for a rendering technique. Such a technique may be part of a
 * core glTF 1.0 asset, an extension of a glTF 2.0 asset, or a custom
 * implementation that is used internally.
 */
public interface TechniqueModel extends NamedModelElement {
    /**
     * Returns the {@link ProgramModel} that is used for implementing this
     * technique
     *
     * @return The {@link ProgramModel}
     */
    ProgramModel getProgramModel();

    /**
     * Returns an unmodifiable map that maps parameter names to
     * {@link TechniqueParametersModel} instances
     *
     * @return The parameters
     */
    Map<String, TechniqueParametersModel> getParameters();

    /**
     * Returns an unmodifiable map that maps attribute names to
     * parameter names
     *
     * @return The mapping from attribute names to parameter names
     */
    Map<String, String> getAttributes();

    /**
     * Returns the {@link TechniqueParametersModel} for the attribute
     * with the given name, or <code>null</code> if no such parameter
     * exists. This is a convenience function and equivalent to
     * <pre><code>
     * techniqueModel.getParameters().get(
     *     techniqueModel.getAttributes().get(attributeName));
     * </code></pre>,
     * handling <code>null</code>-cases accordingly.
     *
     * @param attributeName The attribute name
     * @return The {@link TechniqueParametersModel}
     */
    TechniqueParametersModel getAttributeParameters(String attributeName);

    /**
     * Returns an unmodifiable map that maps uniform names to
     * parameter names
     *
     * @return The uniforms
     */
    Map<String, String> getUniforms();

    /**
     * Returns the {@link TechniqueParametersModel} for the uniform
     * with the given name, or <code>null</code> if no such parameter
     * exists. This is a convenience function and equivalent to
     * <pre><code>
     * techniqueModel.getParameters().get(
     *     techniqueModel.getUniforms().get(uniformName));
     * </code></pre>,
     * handling <code>null</code>-cases accordingly.
     *
     * @param uniformName The uniform name
     * @return The {@link TechniqueParametersModel}
     */
    TechniqueParametersModel getUniformParameters(String uniformName);

    /**
     * Returns the {@link TechniqueStatesModel}
     *
     * @return The {@link TechniqueStatesModel}
     */
    TechniqueStatesModel getTechniqueStatesModel();
}

