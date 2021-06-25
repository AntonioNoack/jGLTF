package de.javagl.jgltf.model;

/**
 * Interface for a texture in a glTF asset
 */
public interface TextureModel extends NamedModelElement {
    /**
     * Return the magnification filter constant
     *
     * @return The constant
     */
    Integer getMagFilter();

    /**
     * Return the minification filter constant
     *
     * @return The constant
     */
    Integer getMinFilter();

    /**
     * Return the wrapping constant for S-direction
     *
     * @return The constant
     */
    int getWrapS();

    /**
     * Return the wrapping constant for T-direction
     *
     * @return The constant
     */
    int getWrapT();

    /**
     * Returns the {@link ImageModel} that backs this texture
     *
     * @return The {@link ImageModel}
     */
    ImageModel getImageModel();
}