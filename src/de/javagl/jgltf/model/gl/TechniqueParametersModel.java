package de.javagl.jgltf.model.gl;

import de.javagl.jgltf.model.NodeModel;

/**
 * An interface for describing {@link TechniqueModel} parameters
 */
public interface TechniqueParametersModel {
    /**
     * Returns the type of the parameter, as a GL constant. For example,
     * <code>GL_INT</code> or <code>GL_FLOAT_VEC3</code>
     *
     * @return The type
     */
    int getType();

    /**
     * Returns the count
     *
     * @return The count
     */
    int getCount();

    /**
     * Returns the string describing the {@link Semantic} of this parameter.
     * This may be a string that starts with an underscore <code>"_"</code>,
     * indicating a custom semantic
     *
     * @return The {@link Semantic} string
     */
    String getSemantic();

    /**
     * Returns the value of this parameter
     *
     * @return The value
     */
    Object getValue();

    /**
     * Returns the {@link NodeModel} of the node that this parameter
     * refers to. This is, for example, used for computing the
     * {@link Semantic#MODEL} matrix.
     *
     * @return The {@link NodeModel}
     */
    NodeModel getNodeModel();
}

