package de.javagl.jgltf.model.io;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Iterator;

/**
 * Utility methods to find <code>ImageReader</code> instances for given
 * image data.<br>
 * <br>
 * This class should not be considered as part of the public API. It may
 * change or be omitted in the future.
 */
public class ImageReaders {
    /**
     * Tries to find an <code>ImageReader</code> that is capable of reading
     * the given image data. The returned image reader will be initialized
     * by passing an ImageInputStream that is created from the given data
     * to its <code>setInput</code> method. The caller is responsible for
     * disposing the returned image reader.
     *
     * @param imageData The image data
     * @return The image reader
     * @throws IOException If no matching image reader can be found
     */
    @SuppressWarnings("resource")
    public static ImageReader findImageReader(ByteBuffer imageData)
            throws IOException {
        InputStream inputStream =
                Buffers.createByteBufferInputStream(imageData.slice());
        ImageInputStream imageInputStream =
                ImageIO.createImageInputStream(inputStream);
        Iterator<ImageReader> imageReaders =
                ImageIO.getImageReaders(imageInputStream);
        if (imageReaders.hasNext()) {
            ImageReader imageReader = imageReaders.next();
            imageReader.setInput(imageInputStream);
            return imageReader;
        }
        throw new IOException("Could not find ImageReader for image data");
    }

    /**
     * Private constructor to prevent instantiation
     */
    private ImageReaders() {
        // Private constructor to prevent instantiation
    }
}
