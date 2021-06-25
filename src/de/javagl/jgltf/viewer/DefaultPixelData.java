package de.javagl.jgltf.viewer;

import java.nio.ByteBuffer;

/**
 * Default implementation of a {@link PixelData}
 */
final class DefaultPixelData implements PixelData
{
    /**
     * The width
     */
    private final int width;
    
    /**
     * The height
     */
    private final int height;
    
    /**
     * The pixels, as RGBA values
     */
    private final ByteBuffer pixelsRGBA;
    
    /**
     * Creates a new instance
     * 
     * @param width The width
     * @param height The height
     * @param pixelsRGBA The pixels, as RGBA values
     */
    DefaultPixelData(int width, int height, ByteBuffer pixelsRGBA)
    {
        this.width = width;
        this.height = height;
        this.pixelsRGBA = pixelsRGBA;
    }
    
    @Override
    public int getWidth()
    {
        return width;
    }

    @Override
    public int getHeight()
    {
        return height;
    }

    @Override
    public ByteBuffer getPixelsRGBA()
    {
        // The slice is BIG_ENDIAN by default
        return pixelsRGBA.slice();
    }

}
