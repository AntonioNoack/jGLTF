package de.javagl.jgltf.model.impl;

import de.javagl.jgltf.model.BufferModel;
import de.javagl.jgltf.model.io.Buffers;

import java.nio.ByteBuffer;

/**
 * Implementation of a {@link BufferModel}
 */
public final class DefaultBufferModel extends AbstractNamedModelElement
        implements BufferModel {
    /**
     * The URI of the buffer data
     */
    private String uri;

    /**
     * The actual data of the buffer
     */
    private ByteBuffer bufferData;

    /**
     * Creates a new instance
     */
    public DefaultBufferModel() {
        // Default constructor
    }

    /**
     * Set the URI for the buffer data
     *
     * @param uri The URI of the buffer data
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * Set the data of this buffer
     *
     * @param bufferData The buffer data
     */
    public void setBufferData(ByteBuffer bufferData) {
        this.bufferData = bufferData;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public int getByteLength() {
        return bufferData.capacity();
    }

    @Override
    public ByteBuffer getBufferData() {
        return Buffers.createSlice(bufferData);
    }

}
