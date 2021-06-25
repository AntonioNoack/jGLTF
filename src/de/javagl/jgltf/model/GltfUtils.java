package de.javagl.jgltf.model;

import de.javagl.jgltf.impl.v1.Asset;
import de.javagl.jgltf.impl.v1.GlTF;

import java.util.Objects;

/**
 * Utility methods related to {@link GlTF} instances
 */
public class GltfUtils {
    /**
     * Returns the version string that is reported by the {@link Asset} in
     * the given {@link GlTF}. If it does not have an {@link Asset}, or
     * the version string in the asset is <code>null</code>, then
     * this method will return the string <code>"1.0.0"</code>.
     *
     * @param gltf The {@link GlTF}
     * @return The version string
     */
    public static String getVersion(GlTF gltf) {
        Objects.requireNonNull(gltf, "The gltf is null");
        Asset asset = gltf.getAsset();
        if (asset == null) {
            return "1.0";
        }
        String version = asset.getVersion();
        if (version == null) {
            return "1.0";
        }
        return version;
    }

    /**
     * Private constructor to prevent instantiation
     */
    private GltfUtils() {
        // Private constructor to prevent instantiation    
    }
}




