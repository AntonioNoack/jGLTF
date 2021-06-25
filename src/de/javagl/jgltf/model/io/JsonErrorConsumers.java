package de.javagl.jgltf.model.io;

import java.util.function.Consumer;

/**
 * Methods to create default consumers of {@link JsonError} instances
 */
public class JsonErrorConsumers {
    /**
     * Create a consumer for {@link JsonError} instances that only prints
     * warning log messages for the errors.
     *
     * @return The consumer
     */
    public static Consumer<JsonError> createLogging() {
        return JacksonUtils.loggingJsonErrorConsumer();
    }

    /**
     * Private constructor to prevent instantiation
     */
    private JsonErrorConsumers() {
        // Private constructor to prevent instantiation
    }
}
