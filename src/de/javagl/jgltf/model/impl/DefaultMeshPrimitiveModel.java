package de.javagl.jgltf.model.impl;

import de.javagl.jgltf.model.AccessorModel;
import de.javagl.jgltf.model.MaterialModel;
import de.javagl.jgltf.model.MeshPrimitiveModel;

import java.util.*;

/**
 * Implementation of a {@link MeshPrimitiveModel}
 */
public final class DefaultMeshPrimitiveModel implements MeshPrimitiveModel {
    /**
     * The attributes of this mesh primitive model
     */
    private final Map<String, AccessorModel> attributes;

    /**
     * The {@link AccessorModel} for the indices data
     */
    private AccessorModel indices;

    /**
     * The {@link MaterialModel} that should be used for rendering
     */
    private MaterialModel materialModel;

    /**
     * The rendering mode
     */
    private final int mode;

    /**
     * The morph targets
     */
    private final List<Map<String, AccessorModel>> targets;

    /**
     * Creates a new instance
     *
     * @param mode The rendering mode
     */
    public DefaultMeshPrimitiveModel(int mode) {
        this.mode = mode;
        this.attributes = new LinkedHashMap<>();
        this.targets = new ArrayList<>();
    }


    /**
     * Put the given {@link AccessorModel} into the attributes, under the
     * given name
     *
     * @param name          The name
     * @param accessorModel The {@link AccessorModel}
     * @return The old value that was stored under the given name
     */
    public AccessorModel putAttribute(String name, AccessorModel accessorModel) {
        Objects.requireNonNull(
                accessorModel, "The accessorModel may not be null");
        return attributes.put(name, accessorModel);
    }

    /**
     * Set the {@link AccessorModel} for the indices
     *
     * @param indices The indices
     */
    public void setIndices(AccessorModel indices) {
        this.indices = indices;
    }

    /**
     * Set the {@link MaterialModel}
     *
     * @param materialModel The {@link MaterialModel}
     */
    public void setMaterialModel(MaterialModel materialModel) {
        this.materialModel = Objects.requireNonNull(
                materialModel, "The materialModel may not be null");
    }

    /**
     * Add the given morph target. A reference to the given map will be stored.
     *
     * @param target The target
     */
    public void addTarget(Map<String, AccessorModel> target) {
        Objects.requireNonNull(target, "The target may not be null");
        this.targets.add(target);
    }


    @Override
    public Map<String, AccessorModel> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    @Override
    public AccessorModel getIndices() {
        return indices;
    }

    @Override
    public int getMode() {
        return mode;
    }

    @Override
    public MaterialModel getMaterialModel() {
        return materialModel;
    }

    @Override
    public List<Map<String, AccessorModel>> getTargets() {
        return Collections.unmodifiableList(targets);
    }


}
