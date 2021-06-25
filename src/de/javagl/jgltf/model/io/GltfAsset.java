package de.javagl.jgltf.model.io;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

/**
 * Interface for a low-level representation of a glTF asset, consisting of
 * the (version-specific) JSON part, optional binary data, and references
 * to external data.
 */
public interface GltfAsset {
    /**
     * Returns the version-specific glTF object. This may be a
     * {@link de.javagl.jgltf.impl.v1.GlTF version 1.0 glTF} or
     * or a {@link de.javagl.jgltf.impl.v2.GlTF version 2.0 glTF}
     *
     * @return The glTF
     */
    Object getGltf();

    /**
     * Returns the binary data of this asset, or <code>null</code> if this
     * asset does not have associated binary data.<br>
     * <br>
     * The returned buffer will be a slice of the data that is stored
     * internally. So changes of the contents of the buffer will affect
     * this asset, but changes of the limit or position of the buffer
     * will not affect this asset.
     *
     * @return the optional binary data
     */
    ByteBuffer getBinaryData();

    /**
     * Return a list of all {@link GltfReference} objects that refer to
     * external resources for this asset
     *
     * @return The {@link GltfReference} objects
     */
    List<GltfReference> getReferences();

    /**
     * Returns the byte buffer containing the data of the external resource
     * with the given (relative!) URI, or <code>null</code> if there is
     * no such data.<br>
     * <br>
     * The returned buffer will be a slice of the data that is stored
     * internally. So changes of the contents of the buffer will affect
     * this asset, but changes of the limit or position of the buffer
     * will not affect this asset.
     *
     * @param uriString The URI string
     * @return The byte buffer
     */
    ByteBuffer getReferenceData(String uriString);

    /**
     * Returns an unmodifiable view on the mapping from relative URI strings
     * to the byte buffers containing the data of the external resources.<br>
     * <br>
     * <b>Callers may not modify the values of this map. That is, the
     * positions or limits of the returned buffers may not be modified!</b>
     *
     * @return The reference data mapping
     */
    Map<String, ByteBuffer> getReferenceDatas();

}
