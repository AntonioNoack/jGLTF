package de.javagl.jgltf.model.io;

import com.fasterxml.jackson.core.JsonStreamContext;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A class containing information about an error that happened
 * during JSON parsing
 */
public final class JsonError {
    /**
     * The error message
     */
    private final String message;

    /**
     * The JSON stream context
     */
    private final List<String> jsonPath;

    /**
     * An optional throwable associated with the error
     */
    private final Throwable throwable;

    /**
     * Default constructor
     *
     * @param message           The error message
     * @param jsonStreamContext The JSON stream context
     * @param throwable         An optional throwable associated with the error
     */
    JsonError(String message, JsonStreamContext jsonStreamContext,
              Throwable throwable) {
        this.message = message;
        this.jsonPath = Collections.unmodifiableList(
                createJsonPath(jsonStreamContext));
        this.throwable = throwable;
    }

    /**
     * Returns the error message
     *
     * @return The error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns an unmodifiable list containing the tokens describing the
     * JSON path where the error occurred
     *
     * @return The JSON path
     */
    public List<String> getJsonPath() {
        return jsonPath;
    }

    /**
     * Returns a short string representation of the {@link #getJsonPath()}
     *
     * @return The string
     */
    public String getJsonPathString() {
        return String.join(".", jsonPath);
    }

    /**
     * Returns the throwable that is associated with the error.
     * This may be <code>null</code>.
     *
     * @return The throwable associated with the error
     */
    public Throwable getThrowable() {
        return throwable;
    }


    /**
     * Create a list of strings describing the JSON path for the given
     * stream context
     *
     * @param streamContext The stream context
     * @return The string list
     */
    private static List<String> createJsonPath(JsonStreamContext streamContext) {
        Collection<JsonStreamContext> list = expand(streamContext);
        return list.stream()
                .map(c -> c.getCurrentName() == null ?
                        "" : c.getCurrentName())
                .collect(Collectors.toList());
    }

    /**
     * Create a collection consisting of stream contexts, starting at the root
     * node and ending at the given stream context
     *
     * @param streamContext The stream context
     * @return The collection
     */
    private static Collection<JsonStreamContext> expand(
            JsonStreamContext streamContext) {
        Deque<JsonStreamContext> collection =
                new LinkedList<>();
        JsonStreamContext current = streamContext;
        while (current != null) {
            collection.addFirst(current);
            current = current.getParent();
        }
        return collection;
    }

}