package de.javagl.jgltf.model.v1;

import de.javagl.jgltf.impl.v1.GlTF;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Utility methods for generating IDs for {@link GlTF} objects
 */
public class GltfIds {
    /**
     * Generate an unspecified ID string with the given prefix that is not
     * yet contained in the key set of the given map
     *
     * @param prefix The prefix for the ID string
     * @param map    The map from the existing IDs. This may be <code>null</code>.
     * @return The new ID
     */
    public static String generateId(
            String prefix, Map<? extends String, ?> map) {
        Set<? extends String> set = Collections.emptySet();
        if (map != null) {
            set = map.keySet();
        }
        return generateId(prefix, set);
    }

    /**
     * Generate an unspecified ID string with the given prefix that is not
     * yet contained in the given set
     *
     * @param prefix The prefix for the ID string
     * @param set    The set of the existing IDs. This may be <code>null</code>.
     * @return The new ID
     */
    public static String generateId(
            String prefix, Set<? extends String> set) {
        Set<? extends String> localSet = Collections.emptySet();
        if (set != null) {
            localSet = set;
        }
        int counter = localSet.size();
        while (true) {
            String id = prefix + counter;
            if (!localSet.contains(id)) {
                return id;
            }
            counter++;
        }
    }

    /**
     * Private constructor to prevent instantiation
     */
    private GltfIds() {
        // Private constructor to prevent instantiation
    }

}