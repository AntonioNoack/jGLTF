package de.javagl.jgltf.model;

import java.util.List;

/**
 * Interface for a model that was created from a glTF asset
 */
public interface GltfModel {
    /**
     * Returns an unmodifiable view on the list of {@link AccessorModel}
     * instances that have been created for the glTF.
     *
     * @return The {@link AccessorModel} instances
     */
    List<AccessorModel> getAccessorModels();

    /**
     * Returns an unmodifiable view on the list of {@link AnimationModel}
     * instances that have been created for the glTF.
     *
     * @return The {@link AnimationModel} instances
     */
    List<AnimationModel> getAnimationModels();

    /**
     * Returns an unmodifiable view on the list of {@link BufferModel}
     * instances that have been created for the glTF.
     *
     * @return The {@link BufferModel} instances
     */
    List<BufferModel> getBufferModels();

    /**
     * Returns an unmodifiable view on the list of {@link BufferViewModel}
     * instances that have been created for the glTF.
     *
     * @return The {@link BufferViewModel} instances
     */
    List<BufferViewModel> getBufferViewModels();

    /**
     * Returns an unmodifiable view on the list of {@link CameraModel}
     * instances that have been created for the glTF.
     *
     * @return The {@link CameraModel} instances
     */
    List<CameraModel> getCameraModels();

    /**
     * Returns an unmodifiable view on the list of {@link ImageModel}
     * instances that have been created for the glTF.
     *
     * @return The {@link ImageModel} instances
     */
    List<ImageModel> getImageModels();

    /**
     * Returns an unmodifiable view on the list of {@link MaterialModel}
     * instances that have been created for the glTF.
     *
     * @return The {@link MaterialModel} instances
     */
    List<MaterialModel> getMaterialModels();

    /**
     * Returns an unmodifiable view on the list of {@link NodeModel}
     * instances that have been created for the glTF.
     *
     * @return The {@link NodeModel} instances
     */
    List<NodeModel> getNodeModels();

    /**
     * Returns an unmodifiable view on the list of {@link SceneModel}
     * instances that have been created for the glTF.
     *
     * @return The {@link SceneModel} instances
     */
    List<SceneModel> getSceneModels();

    /**
     * Returns an unmodifiable view on the list of {@link TextureModel}
     * instances that have been created for the glTF.
     *
     * @return The {@link TextureModel} instances
     */
    List<TextureModel> getTextureModels();
}

