package de.javagl.jgltf.model.gl.impl;

import de.javagl.jgltf.model.NodeModel;
import de.javagl.jgltf.model.gl.Semantic;
import de.javagl.jgltf.model.gl.TechniqueParametersModel;

/**
 * Implementation of a {@link TechniqueParametersModel}
 */
public class DefaultTechniqueParametersModel implements TechniqueParametersModel {
    /**
     * The type
     */
    private final int type;

    /**
     * The count
     */
    private final int count;

    /**
     * The {@link Semantic} semantic
     */
    private final String semantic;

    /**
     * The value
     */
    private final Object value;

    /**
     * The {@link NodeModel}
     */
    private final NodeModel nodeModel;

    /**
     * Default constructor
     *
     * @param type      The type
     * @param count     The count
     * @param semantic  The {@link Semantic}
     * @param value     The value
     * @param nodeModel The {@link NodeModel}
     */
    public DefaultTechniqueParametersModel(
            int type, int count, String semantic,
            Object value, NodeModel nodeModel) {
        this.type = type;
        this.count = count;
        this.semantic = semantic;
        this.value = value;
        this.nodeModel = nodeModel;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public String getSemantic() {
        return semantic;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public NodeModel getNodeModel() {
        return nodeModel;
    }

}
