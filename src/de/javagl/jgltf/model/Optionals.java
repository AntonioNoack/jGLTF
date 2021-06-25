package de.javagl.jgltf.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Utility methods related to values that are optional. These values may
 * be <code>null</code>, and the utility methods will provide default
 * values in this case.
 */
public class Optionals {
    /**
     * Returns the given list, or an unmodifiable empty list if the
     * given list is <code>null</code>.
     *
     * @param <T>  The element type
     * @param list The list
     * @return The result
     */
    public static <T> List<T> of(List<T> list) {
        return of(list, Collections.emptyList());
    }

    /**
     * Returns the specified element from the given list, or <code>null</code>
     * if the list is <code>null</code> or the index is not valid.
     *
     * @param <T>   The element type
     * @param list  The list
     * @param index The index
     * @return The element
     */
    public static <T> T get(List<T> list, int index) {
        if (list == null) {
            return null;
        }
        if (index < 0) {
            return null;
        }
        if (index >= list.size()) {
            return null;
        }
        return list.get(index);
    }

    /**
     * Returns the given map, or an unmodifiable empty map if the
     * given map is <code>null</code>.
     *
     * @param <K> The key type
     * @param <V> The value type
     * @param map The map
     * @return The result
     */
    public static <K, V> Map<K, V> of(Map<K, V> map) {
        return of(map, Collections.emptyMap());
    }

    /**
     * Returns the given value, or the default value if the given value
     * is <code>null</code>.
     *
     * @param <T>          The value type
     * @param value        The value
     * @param defaultValue The default value
     * @return The result
     */
    public static <T> T of(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    /**
     * Returns the value that is associated with the given key in the
     * given map, or <code>null</code> if either the key or the map
     * is <code>null</code>.
     *
     * @param <V> The value type
     * @param key The key
     * @param map The map
     * @return The value
     */
    public static <V> V get(Object key, Map<?, V> map) {
        if (key == null) {
            return null;
        }
        if (map == null) {
            return null;
        }
        return map.get(key);
    }

    /**
     * Returns a clone of the given array, or <code>null</code> if the
     * given array is <code>null</code>
     *
     * @param array The array
     * @return The result
     */
    public static float[] clone(float[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * Private constructor to prevent instantiation
     */
    private Optionals() {
        // Private constructor to prevent instantiation
    }

}
