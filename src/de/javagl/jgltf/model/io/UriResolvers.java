package de.javagl.jgltf.model.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.function.Function;
import de.javagl.jgltf.logging.Logger;

/**
 * Methods for creating functions that resolve URI to byte buffers
 */
public class UriResolvers {
    /**
     * The logger used in this class
     */
    private static final Logger logger =
            Logger.getLogger(UriResolvers.class);

    /**
     * Creates a function that resolves URI strings against the given
     * base URI, and returns a byte buffer containing the data from
     * the resulting URI.<br>
     * <br>
     * The given URI strings may either be standard URI or data URI.<br>
     * <br>
     * If the returned function cannot read the data, then it will print a
     * warning and return <code>null</code>.
     *
     * @param baseUri The base URI to resolve against
     * @return The function
     */
    public static Function<String, ByteBuffer> createBaseUriResolver(
            URI baseUri) {
        Objects.requireNonNull(baseUri, "The baseUri may not be null");
        Function<String, InputStream> inputStreamFunction =
                uriString -> {
                    try {
                        URI absoluteUri = IO.makeAbsolute(baseUri, uriString);
                        return IO.createInputStream(absoluteUri);
                    } catch (IOException e) {
                        logger.warning("Could not open input stream for URI "
                                + uriString + ":  " + e.getMessage());
                        return null;
                    }
                };
        return reading(inputStreamFunction);
    }

    /**
     * Create a function that maps a string to the input stream of a resource
     * of the given class.
     *
     * @param c The class
     * @return The resolving function
     */
    public static Function<String, ByteBuffer> createResourceUriResolver(
            Class<?> c) {
        Objects.requireNonNull(c, "The class may not be null");
        Function<String, InputStream> inputStreamFunction =
                uriString -> {
                    InputStream inputStream =
                            c.getResourceAsStream("/" + uriString);
                    if (inputStream == null) {
                        logger.warning(
                                "Could not obtain input stream for resource "
                                        + "with URI " + uriString);
                    }
                    return inputStream;
                };
        return reading(inputStreamFunction);
    }

    /**
     * Returns a function that reads the data from the input stream that is
     * provided by the given delegate, and returns this data as a direct
     * byte buffer.<br>
     * <br>
     * If the delegate returns <code>null</code>, or an input stream that
     * cannot be read, then the function will print a warning and return
     * <code>null</code>.
     *
     * @param inputStreamFunction The input stream function
     * @return The function for reading the input stream data
     */
    private static <T> Function<T, ByteBuffer> reading(
            Function<? super T, ? extends InputStream> inputStreamFunction) {
        return t -> {
            try (InputStream inputStream = inputStreamFunction.apply(t)) {
                if (inputStream == null) {
                    logger.warning("The input stream was null");
                    return null;
                }
                byte[] data = IO.readStream(inputStream);
                return Buffers.create(data);
            } catch (IOException e) {
                logger.warning("Could not read from input stream: "
                        + e.getMessage());
                return null;
            }
        };
    }

    /**
     * Private constructor to prevent instantiation
     */
    private UriResolvers() {
        // Private constructor to prevent instantiation
    }

}
