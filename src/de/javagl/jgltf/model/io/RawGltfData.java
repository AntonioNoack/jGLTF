package de.javagl.jgltf.model.io;

import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * The raw data of a glTF asset, consisting of the raw JSON data and the binary
 * data. This data is still independent of the actual glTF version, and covers
 * both standard (JSON) and binary glTF.<br>
 * <br>
 * Instances of this class are returned by the {@link RawGltfDataReader}.
 * Clients will usually not use this class directly.
 */
public final class RawGltfData {
    /**
     * The JSON data
     */
    private final ByteBuffer jsonData;

    /**
     * The optional binary data
     */
    private final ByteBuffer binaryData;

    /**
     * Default constructor. References to the buffers will be stored.
     * So they should have their position and limit set accordingly,
     * and may not be modified after being passed to this constructor.
     *
     * @param jsonData   The JSON data
     * @param binaryData The optional binary data
     */
    public RawGltfData(ByteBuffer jsonData, ByteBuffer binaryData) {
        this.jsonData = Objects.requireNonNull(
                jsonData, "The jsonData may not be null");
        this.binaryData = binaryData;
    }

    /**
     * Returns the JSON data. <br>
     * <br>
     * The returned buffer will be a slice of the data that is stored
     * internally. This means that changes of the data will affect this
     * instance, but changes of the position or limit of the returned
     * buffer will not affect this instance.
     *
     * @return The JSON data
     */
    public ByteBuffer getJsonData() {
        return Buffers.createSlice(jsonData);
    }

    /**
     * Returns the binary data. This may be <code>null</code> if the asset
     * was created from a JSON string only.<br>
     * <br>
     * The returned buffer will be a slice of the data that is stored
     * internally. This means that changes of the data will affect this
     * instance, but changes of the position or limit of the returned
     * buffer will not affect this instance.
     *
     * @return The binary data
     */
    public ByteBuffer getBinaryData() {
        return Buffers.createSlice(binaryData);
    }
}
