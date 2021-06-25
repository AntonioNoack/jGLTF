package de.javagl.jgltf.model;

import java.util.Objects;

/**
 * #ANNO no reason for it to be private
 * A very simple (package-private!) bounding box implementation
 */
public class BoundingBox {

    /**
     * The minimum x coordinate
     */
    private float minX;

    /**
     * The minimum y coordinate
     */
    private float minY;

    /**
     * The minimum z coordinate
     */
    private float minZ;

    /**
     * The maximum x coordinate
     */
    private float maxX;

    /**
     * The maximum y coordinate
     */
    private float maxY;

    /**
     * The maximum z coordinate
     */
    private float maxZ;

    /**
     * Creates a bounding box
     */
    public BoundingBox() {
        minX = Float.MAX_VALUE;
        minY = Float.MAX_VALUE;
        minZ = Float.MAX_VALUE;
        maxX = -Float.MAX_VALUE;
        maxY = -Float.MAX_VALUE;
        maxZ = -Float.MAX_VALUE;
    }

    /**
     * Combine this bounding box with the given point
     *
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @param z The z-coordinate
     */
    public void combine(float x, float y, float z) {
        minX = Math.min(minX, x);
        minY = Math.min(minY, y);
        minZ = Math.min(minZ, z);
        maxX = Math.max(maxX, x);
        maxY = Math.max(maxY, y);
        maxZ = Math.max(maxZ, z);
    }

    /**
     * Combine this bounding box with the given one
     *
     * @param other The other bounding box
     */
    public void combine(BoundingBox other) {
        Objects.requireNonNull(other, "The other bounding box may not be null");
        minX = Math.min(minX, other.getMinX());
        minY = Math.min(minY, other.getMinY());
        minZ = Math.min(minZ, other.getMinZ());
        maxX = Math.max(maxX, other.getMaxX());
        maxY = Math.max(maxY, other.getMaxY());
        maxZ = Math.max(maxZ, other.getMaxZ());
    }

    /**
     * Returns the x-coordinate of the center
     *
     * @return The x-coordinate of the center
     */
    public float getCenterX() {
        return getMinX() + getSizeX() * 0.5f;
    }

    /**
     * Returns the y-coordinate of the center
     *
     * @return The y-coordinate of the center
     */
    public float getCenterY() {
        return getMinY() + getSizeY() * 0.5f;
    }

    /**
     * Returns the z-coordinate of the center
     *
     * @return The z-coordinate of the center
     */
    public float getCenterZ() {
        return getMinZ() + getSizeZ() * 0.5f;
    }

    /**
     * Returns the size in x-direction
     *
     * @return The size in x-direction
     */
    public float getSizeX() {
        return getMaxX() - getMinX();
    }

    /**
     * Returns the size in y-direction
     *
     * @return The size in y-direction
     */
    public float getSizeY() {
        return getMaxY() - getMinY();
    }

    /**
     * Returns the size in z-direction
     *
     * @return The size in z-direction
     */
    public float getSizeZ() {
        return getMaxZ() - getMinZ();
    }

    /**
     * Returns the minimum x coordinate
     *
     * @return The minimum x coordinate
     */
    public float getMinX() {
        return minX;
    }

    /**
     * Returns the minimum y coordinate
     *
     * @return The minimum y coordinate
     */
    public float getMinY() {
        return minY;
    }

    /**
     * Returns the minimum z coordinate
     *
     * @return The minimum z coordinate
     */
    public float getMinZ() {
        return minZ;
    }

    /**
     * Returns the maximum x coordinate
     *
     * @return The maximum x coordinate
     */
    public float getMaxX() {
        return maxX;
    }

    /**
     * Returns the maximum y coordinate
     *
     * @return The maximum y coordinate
     */
    public float getMaxY() {
        return maxY;
    }

    /**
     * Returns the maximum z coordinate
     *
     * @return The maximum z coordinate
     */
    public float getMaxZ() {
        return maxZ;
    }

    @Override
    public String toString() {
        return "[(" +
                getMinX() + "," + getMinY() + "," + getMinZ() + ")-(" +
                getMaxX() + "," + getMaxY() + "," + getMaxZ() + ")]";
    }
}