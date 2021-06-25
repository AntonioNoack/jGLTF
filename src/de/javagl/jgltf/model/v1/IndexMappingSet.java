package de.javagl.jgltf.model.v1;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Helper class storing a set of index mappings, identified via names.
 * Each index mapping maps string IDs to integer values.
 */
final class IndexMappingSet {
    /**
     * The index mappings
     */
    private final Map<Object, Map<String, Integer>> indexMappings;

    /**
     * Default constructor
     */
    IndexMappingSet() {
        indexMappings = new LinkedHashMap<>();
    }

    /**
     * Return the index mapping for the given name, creating it if necessary
     *
     * @param name The name
     * @return The index mapping
     */
    private Map<String, Integer> get(Object name) {
        return indexMappings.computeIfAbsent(name,
                n -> new LinkedHashMap<>());
    }

    /**
     * Generate an index mapping for the given name. This mapping will map
     * the keys of the given map to consecutive integer values, starting
     * with 0, in iteration order.
     *
     * @param name The name
     * @param map  The map to initialize the mapping from
     */
    void generate(Object name, Map<String, ?> map) {
        if (map != null) {
            get(name).putAll(IndexMappings.computeIndexMapping(map));
        }
    }

    /**
     * Returns the integer that is stored in the index mapping under the
     * given key, in the index mapping that is identified with
     * the given map name
     *
     * @param name The name of the index mapping
     * @param key  The key to look up in the index mapping
     * @return The index
     */
    Integer getIndex(String name, String key) {
        if (key == null) {
            return null;
        }
        return get(name).get(key);
    }


}