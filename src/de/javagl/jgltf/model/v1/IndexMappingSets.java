package de.javagl.jgltf.model.v1;

import de.javagl.jgltf.impl.v1.GlTF;

/**
 * Utility methods to create {@link IndexMappingSet} instances
 */
class IndexMappingSets {
    /**
     * Compute the {@link IndexMappingSet} for the given glTF instance.
     * The {@link IndexMappingSet} will contain index mappings for all
     * top-level dictionaries of the given glTF.
     *
     * @param gltf The glTF
     * @return The {@link IndexMappingSet}
     */
    static IndexMappingSet create(GlTF gltf) {
        IndexMappingSet indexMappingSet = new IndexMappingSet();
        indexMappingSet.generate("accessors", gltf.getAccessors());
        indexMappingSet.generate("animations", gltf.getAnimations());
        indexMappingSet.generate("buffers", gltf.getBuffers());
        indexMappingSet.generate("bufferViews", gltf.getBufferViews());
        indexMappingSet.generate("cameras", gltf.getCameras());
        indexMappingSet.generate("images", gltf.getImages());
        indexMappingSet.generate("materials", gltf.getMaterials());
        indexMappingSet.generate("meshes", gltf.getMeshes());
        indexMappingSet.generate("nodes", gltf.getNodes());
        indexMappingSet.generate("programs", gltf.getPrograms());
        indexMappingSet.generate("samplers", gltf.getSamplers());
        indexMappingSet.generate("scenes", gltf.getScenes());
        indexMappingSet.generate("shaders", gltf.getShaders());
        indexMappingSet.generate("skins", gltf.getSkins());
        indexMappingSet.generate("techniques", gltf.getTechniques());
        indexMappingSet.generate("textures", gltf.getTextures());
        return indexMappingSet;
    }

    /**
     * Private constructor to prevent instantiation
     */
    private IndexMappingSets() {
        // Private constructor to prevent instantiation
    }
}
