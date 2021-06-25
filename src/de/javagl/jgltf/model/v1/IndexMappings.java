package de.javagl.jgltf.model.v1;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Utility methods to compute index mappings
 */
class IndexMappings {
    /**
     * Compute the index mapping for the given map. If the given map is
     * <code>null</code>, then an empty map will be returned. Otherwise,
     * the method will iterate through the given map, and return a
     * map where the keys of the given map are associated with
     * consecutive integers, starting with 0, in iteration order.
     *
     * @param map The input map
     * @return The index mapping
     */
    static Map<String, Integer> computeIndexMapping(Map<String, ?> map) {
        if (map == null) {
            return Collections.emptyMap();
        }
        Map<String, Integer> indexMapping =
                new LinkedHashMap<>();
        int indexCounter = 0;
        for (String key : map.keySet()) {
            indexMapping.put(key, indexCounter);
            indexCounter++;
        }
        return indexMapping;
    }

    /**
     * Private constructor to prevent instantiation
     */
    private IndexMappings() {
        // Private constructor to prevent instantiation
    }
}
