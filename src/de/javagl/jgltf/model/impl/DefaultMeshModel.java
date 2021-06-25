package de.javagl.jgltf.model.impl;

import de.javagl.jgltf.model.MeshModel;
import de.javagl.jgltf.model.MeshPrimitiveModel;
import de.javagl.jgltf.model.Optionals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of a {@link MeshModel}
 */
public final class DefaultMeshModel extends AbstractNamedModelElement
        implements MeshModel {
    /**
     * The {@link MeshPrimitiveModel} instances
     */
    private final List<MeshPrimitiveModel> meshPrimitiveModels;

    /**
     * The morph target weights
     */
    private float[] weights;

    /**
     * Creates a new instance
     */
    public DefaultMeshModel() {
        this.meshPrimitiveModels = new ArrayList<>();
    }

    /**
     * Add a {@link MeshPrimitiveModel}
     *
     * @param meshPrimitiveModel The {@link MeshPrimitiveModel} to add
     */
    public void addMeshPrimitiveModel(MeshPrimitiveModel meshPrimitiveModel) {
        this.meshPrimitiveModels.add(meshPrimitiveModel);
    }

    /**
     * Set the default morph target weights to be a copy of the given array,
     * or <code>null</code> if the given array is <code>null</code>.
     *
     * @param weights the default morph target weights
     */
    public void setWeights(float[] weights) {
        this.weights = Optionals.clone(weights);
    }

    @Override
    public List<MeshPrimitiveModel> getMeshPrimitiveModels() {
        return Collections.unmodifiableList(meshPrimitiveModels);
    }

    @Override
    public float[] getWeights() {
        return Optionals.clone(weights);
    }

}
