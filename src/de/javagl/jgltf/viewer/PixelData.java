package de.javagl.jgltf.viewer;

import java.nio.ByteBuffer;

// TODO: This interface is supposed to allow an abstraction of pixel
// data for future implementations that are targeting Android. There,
// the BufferedImage class is not available, so it should not appear
// in the interfaces of the model- or viewer classes.

/**
 * An interface describing (RGBA) pixel data for an image 
 */
interface PixelData
{
    /**
     * Returns the width of the image
     * 
     * @return The width
     */
    int getWidth();

    /**
     * Returns the height of the image
     * 
     * @return The height
     */
    int getHeight();

    /**
     * Returns a new slice of the direct byte buffer containing the pixel 
     * data, as RGBA values
     * 
     * @return The pixels
     */
    ByteBuffer getPixelsRGBA();
}
