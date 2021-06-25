
package de.javagl.jgltf.model;

import de.javagl.jgltf.model.gl.TechniqueModel;

import java.util.Map;

/**
 * Interface for a material model
 */
public interface MaterialModel extends NamedModelElement {
    /**
     * Returns the {@link TechniqueModel}
     *
     * @return The {@link TechniqueModel}
     */
    TechniqueModel getTechniqueModel();

    /**
     * Returns an unmodifiable (possibly empty) map containing parameter values
     *
     * @return The parameter values
     */
    Map<String, Object> getValues();
}
