package de.javagl.jgltf.model;

/**
 * An exception that may be thrown to indicate an error inside a glTF asset.
 */
public class GltfException extends RuntimeException {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = -1052127064537015753L;

    /**
     * Creates a new exception with the given message
     *
     * @param message The message
     */
    public GltfException(String message) {
        super(message);
    }

    /**
     * Creates a new exception with the given message and cause
     *
     * @param message The message
     * @param cause   The cause
     */
    public GltfException(String message, Throwable cause) {
        super(message, cause);
    }
}
