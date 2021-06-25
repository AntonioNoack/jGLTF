package de.javagl.jgltf.model;

import java.util.List;
import java.util.Map;

/**
 * Interface for a primitive that is part of a mesh
 */
public interface MeshPrimitiveModel {
    /**
     * Returns an unmodifiable view on the mapping from attribute names to
     * the {@link AccessorModel} instances for the attribute data
     *
     * @return The attributes mapping
     */
    Map<String, AccessorModel> getAttributes();

    /**
     * Return an {@link AccessorModel} for the indices, or <code>null</code>
     * if this primitive describes non-indexed geometry
     *
     * @return The indices
     */
    AccessorModel getIndices();

    /**
     * Returns the rendering mode, as a (GL) constant, standing for
     * <code>GL_POINTS</code>, <code>GL_TRIANGLES</code> etc.
     *
     * @return The rendering mode
     */
    int getMode();

    /**
     * Returns the {@link MaterialModel} that should be used for rendering
     * this mesh primitive
     *
     * @return The {@link MaterialModel}
     */
    MaterialModel getMaterialModel();

    /**
     * Returns an unmodifiable view on the list of morph targets. Each element
     * of this list will be an unmodifiable map. Each map maps the attribute
     * name to the {@link AccessorModel} that provides the morph target data.
     *
     * @return The morph targets
     */
    List<Map<String, AccessorModel>> getTargets();
}
