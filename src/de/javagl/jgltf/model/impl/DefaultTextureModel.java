package de.javagl.jgltf.model.impl;

import de.javagl.jgltf.model.ImageModel;
import de.javagl.jgltf.model.TextureModel;

/**
 * Implementation of a {@link TextureModel}
 */
public final class DefaultTextureModel extends AbstractNamedModelElement
        implements TextureModel {
    /**
     * The magnification filter constant
     */
    private final Integer magFilter;

    /**
     * The minification filter constant
     */
    private final Integer minFilter;

    /**
     * The wrapping constant for the S-direction
     */
    private final int wrapS;

    /**
     * The wrapping constant for the T-direction
     */
    private final int wrapT;

    /**
     * The {@link ImageModel}
     */
    private ImageModel imageModel;

    /**
     * Creates a new instance
     *
     * @param magFilter The optional magnification filter
     * @param minFilter The optional minification filter
     * @param wrapS     The S-wrapping
     * @param wrapT     The T-wrapping
     */
    public DefaultTextureModel(
            Integer magFilter, Integer minFilter, int wrapS, int wrapT) {
        this.magFilter = magFilter;
        this.minFilter = minFilter;
        this.wrapS = wrapS;
        this.wrapT = wrapT;
    }

    /**
     * Set the {@link ImageModel}
     *
     * @param imageModel The {@link ImageModel}
     */
    public void setImageModel(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    @Override
    public Integer getMagFilter() {
        return magFilter;
    }

    @Override
    public Integer getMinFilter() {
        return minFilter;
    }

    @Override
    public int getWrapS() {
        return wrapS;
    }

    @Override
    public int getWrapT() {
        return wrapT;
    }

    @Override
    public ImageModel getImageModel() {
        return imageModel;
    }
}
