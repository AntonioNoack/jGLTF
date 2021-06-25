package de.javagl.jgltf.model;

import java.nio.ByteBuffer;

/**
 * Interface for an image in a glTF asset
 */
public interface ImageModel extends NamedModelElement {
    /**
     * Returns the URI of the image data (optional)
     *
     * @return The URI
     */
    String getUri();

    /**
     * Returns the MIME type of the image data that is contained in
     * the buffer view
     *
     * @return The MIME type
     */
    String getMimeType();

    /**
     * Returns the (optional) {@link BufferViewModel} that contains
     * the image data
     *
     * @return The {@link BufferViewModel}
     */
    BufferViewModel getBufferViewModel();

    /**
     * Returns the actual image data. This will return a slice of the
     * buffer that is stored internally. Thus, changes to the contents
     * of this buffer will affect this model, but modifications of the
     * position and limit of the returned buffer will not affect this
     * model.<br>
     *
     * @return The image data
     */
    ByteBuffer getImageData();
}