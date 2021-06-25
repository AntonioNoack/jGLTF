package de.javagl.jgltf.model.io.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.javagl.jgltf.impl.v1.GlTF;
import de.javagl.jgltf.model.io.JacksonUtils;
import de.javagl.jgltf.model.io.JsonError;
import de.javagl.jgltf.model.io.JsonErrorConsumers;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

/**
 * A class for reading a version 1.0 {@link GlTF} from an input stream
 */
public final class GltfReaderV1 {
    // Note: This class could use GltfReader as a delegate, and could
    // then verify that the glTF has the right version. Right now, it
    // assumes that it is only used for glTF 1.0 inputs.

    /**
     * A consumer for {@link JsonError}s that may occur while reading
     * the glTF JSON
     */
    private Consumer<? super JsonError> jsonErrorConsumer =
            JsonErrorConsumers.createLogging();

    /**
     * Creates a new glTF reader
     */
    public GltfReaderV1() {
        // Default constructor
    }

    /**
     * Set the given consumer to receive {@link JsonError}s that may
     * occur when the JSON part of the glTF is read
     *
     * @param jsonErrorConsumer The consumer
     */
    public void setJsonErrorConsumer(
            Consumer<? super JsonError> jsonErrorConsumer) {
        this.jsonErrorConsumer = jsonErrorConsumer;
    }

    /**
     * Read the {@link GlTF} from the given stream
     *
     * @param inputStream The input stream
     * @return The {@link GlTF}
     * @throws IOException If an IO error occurs
     */
    public GlTF read(InputStream inputStream) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonUtils.configure(objectMapper, jsonErrorConsumer);
        return objectMapper.readValue(inputStream, GlTF.class);
    }

}
