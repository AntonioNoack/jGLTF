package de.javagl.jgltf.model;

/**
 * Interface for all classes of the model package that have an optional name.
 * This is the name that was given to the <code>GlTFChildOfRootProperty</code>
 * of the original glTF asset.
 */
public interface NamedModelElement {
    /**
     * Returns the name of this element, or <code>null</code> if this element
     * does not have an associated name.
     *
     * @return The optional name
     */
    String getName();
}
