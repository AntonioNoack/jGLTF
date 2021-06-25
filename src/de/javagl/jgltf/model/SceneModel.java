package de.javagl.jgltf.model;

import java.util.List;

/**
 * Interface for a scene that was read from a glTF asset
 */
public interface SceneModel extends NamedModelElement {
    /**
     * Returns an unmodifiable view on the the list of all root
     * {@link NodeModel} instances of the scene
     *
     * @return The {@link NodeModel} instances
     */
    List<NodeModel> getNodeModels();
}







