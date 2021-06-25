package de.javagl.jgltf.model.impl;

import de.javagl.jgltf.model.NamedModelElement;

/**
 * Abstract base implementation of the {@link NamedModelElement} interface.
 */
public class AbstractNamedModelElement implements NamedModelElement {

    /**
     * The name
     */
    private String name;

    @Override
    public String getName() {
        return name;
    }

    /**
     * Set the name of this model element, or <code>null</code> if this
     * model element does not have a name.
     *
     * @param name The optional name
     */
    public void setName(String name) {
        this.name = name;
    }
}
