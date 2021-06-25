package de.javagl.jgltf.model.impl;

import de.javagl.jgltf.model.BufferViewModel;
import de.javagl.jgltf.model.ImageModel;
import de.javagl.jgltf.model.io.Buffers;

import java.nio.ByteBuffer;

/**
 * Implementation of a {@link ImageModel}
 */
public final class DefaultImageModel extends AbstractNamedModelElement
        implements ImageModel {
    /**
     * The URI of the image
     */
    private String uri;

    /**
     * The MIME type of the image data in the buffer view model
     */
    private final String mimeType;

    /**
     * The {@link BufferViewModel}
     */
    private BufferViewModel bufferViewModel;

    /**
     * The image data
     */
    private ByteBuffer imageData;

    /**
     * Creates a new instance
     *
     * @param mimeType        The MIME type
     * @param bufferViewModel The {@link BufferViewModel}
     */
    public DefaultImageModel(
            String mimeType, BufferViewModel bufferViewModel) {
        this.mimeType = mimeType;
        this.bufferViewModel = bufferViewModel;
    }

    /**
     * Set the URI string of this image
     *
     * @param uri The URI
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * Set the {@link BufferViewModel} that this image refers to
     *
     * @param bufferViewModel The {@link BufferViewModel}
     */
    public void setBufferViewModel(BufferViewModel bufferViewModel) {
        this.bufferViewModel = bufferViewModel;
    }

    /**
     * Set the data of this image. If the given data is <code>null</code>,
     * then calls to {@link #getImageData()} will return the data of the
     * {@link BufferViewModel} that was set with {@link #setBufferViewModel}
     *
     * @param imageData The image data
     */
    public void setImageData(ByteBuffer imageData) {
        this.imageData = imageData;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getMimeType() {
        return mimeType;
    }

    @Override
    public BufferViewModel getBufferViewModel() {
        return bufferViewModel;
    }

    @Override
    public ByteBuffer getImageData() {
        if (imageData == null) {
            return bufferViewModel.getBufferViewData();
        }
        return Buffers.createSlice(imageData);
    }


}
