package de.javagl.jgltf.model;

import java.util.Objects;

/**
 * Utility methods for computing bounding boxes.<br>
 * <br>
 * <b>Note:</b> This class is preliminary. It may be replaced with more
 * sophisticated bounding box computation methods in the future.
 */
public class BoundingBoxes {
    /**
     * Compute the bounding box of the given {@link GltfModel}. The result
     * will be an array <code>[minX, minY, minZ, maxX, maxY, maxZ]</code>.
     *
     * @param gltfModel The {@link GltfModel}
     * @return The bounding box
     */
    public static float[] computeBoundingBoxMinMax(GltfModel gltfModel) {
        Objects.requireNonNull(gltfModel, "The gltfModel may not be null");

        BoundingBoxComputer boundingBoxComputer =
                new BoundingBoxComputer(gltfModel);
        BoundingBox boundingBox = boundingBoxComputer.compute();

        return new float[]{
                boundingBox.getMinX(),
                boundingBox.getMinY(),
                boundingBox.getMinZ(),
                boundingBox.getMaxX(),
                boundingBox.getMaxY(),
                boundingBox.getMaxZ()
        };

    }

    /**
     * Private constructor to prevent instantiation
     */
    private BoundingBoxes() {
        // Private constructor to prevent instantiation
    }
}
