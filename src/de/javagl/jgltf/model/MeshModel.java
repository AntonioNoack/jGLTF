package de.javagl.jgltf.model;

import java.util.List;

/**
 * Interface for a mesh that is part of a glTF asset
 */
public interface MeshModel extends NamedModelElement {
    /**
     * Returns an unmodifiable view on the {@link MeshPrimitiveModel} objects
     * that this mesh consists of
     *
     * @return The {@link MeshPrimitiveModel} objects
     */
    List<MeshPrimitiveModel> getMeshPrimitiveModels();

    /**
     * Returns a copy of the default morph target weights, or <code>null</code>
     * if no default morph target weights have been defined
     *
     * @return A copy of the morph target weights
     */
    float[] getWeights();
}

