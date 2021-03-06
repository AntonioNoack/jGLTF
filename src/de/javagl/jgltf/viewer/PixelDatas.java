package de.javagl.jgltf.viewer;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import de.javagl.jgltf.logging.Logger;

/**
 * Methods to create {@link PixelData} objects from raw image data
 */
class PixelDatas
{
    /**
     * The logger used in this class
     */
    private static final Logger logger =
        Logger.getLogger(PixelDatas.class);
    
    /**
     * Create a {@link PixelData} from the given image data. The image data
     * may for example the the raw data of a JPG or PNG or GIF file. The
     * exact set of supported file formats is not specified. If the given
     * data can not be read, then a warning is printed and <code>null</code>
     * is returned.
     * 
     * @param imageData The image data
     * @return The {@link PixelData}
     */
    static PixelData create(ByteBuffer imageData)
    {
        BufferedImage textureImage = ImageUtils.readAsBufferedImage(imageData);
        if (textureImage == null)
        {
            logger.warning("Could not read image from image data");
            return null;
        }
            
        ByteBuffer pixelDataARGB = 
            ImageUtils.getImagePixelsARGB(textureImage, false);
        ByteBuffer pixelDataRGBA =
            ImageUtils.swizzleARGBtoRGBA(pixelDataARGB);
        int width = textureImage.getWidth();
        int height = textureImage.getHeight();
        return new DefaultPixelData(width, height, pixelDataRGBA);
    }
    
    /**
     * Create an unspecified {@link PixelData} object that may be used as 
     * a placeholder for image data that could not be read
     * 
     * @return The {@link PixelData} object
     */
    static PixelData createErrorPixelData()
    {
        // Right now, this is a 2x2 checkerboard of red and white pixels
        ByteBuffer pixelDataRGBA = ByteBuffer.allocateDirect(4 * Integer.SIZE);
        IntBuffer intPixelDataRGBA = 
            pixelDataRGBA.order(ByteOrder.BIG_ENDIAN).asIntBuffer();
        intPixelDataRGBA.put(0, 0xFF0000FF);
        intPixelDataRGBA.put(1, 0xFFFFFFFF);
        intPixelDataRGBA.put(2, 0xFF0000FF);
        intPixelDataRGBA.put(3, 0xFFFFFFFF);
        int width = 2;
        int height = 2;
        return new DefaultPixelData(width, height, pixelDataRGBA);
    }
    
    
    /**
     * Private constructor to prevent instantiation
     */
    private PixelDatas()
    {
        // Private constructor to prevent instantiation
    }
}
