package de.javagl.jgltf.model;

import java.nio.ByteBuffer;

/**
 * Interface for a buffer of a glTF asset
 */
public interface BufferModel extends NamedModelElement {
    /**
     * Returns the URI of the buffer data
     *
     * @return The URI
     */
    String getUri();

    /**
     * Returns the length, in bytes, of the {@link #getBufferData() buffer data}
     *
     * @return The buffer length, in bytes
     */
    int getByteLength();

    /**
     * Returns the actual buffer data. This will return a slice of the buffer
     * that is stored internally. Thus, changes to the contents of this buffer
     * will affect this model, but modifications of the position and limit of
     * the returned buffer will not affect this model.<br>
     *
     * @return The buffer data
     */
    ByteBuffer getBufferData();

}