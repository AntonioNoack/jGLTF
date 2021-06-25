package de.javagl.jgltf.model.impl;

import de.javagl.jgltf.model.NodeModel;
import de.javagl.jgltf.model.SceneModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of a {@link SceneModel}
 */
public final class DefaultSceneModel extends AbstractNamedModelElement
        implements SceneModel {
    /**
     * The list of root nodes
     */
    private final List<NodeModel> nodeModels;

    /**
     * Creates a new instance
     */
    public DefaultSceneModel() {
        this.nodeModels = new ArrayList<>();
    }

    /**
     * Add the given (root!) {@link NodeModel} to this scene
     *
     * @param node The {@link NodeModel}
     */
    public void addNode(NodeModel node) {
        nodeModels.add(node);
    }

    @Override
    public List<NodeModel> getNodeModels() {
        return Collections.unmodifiableList(nodeModels);
    }

}
