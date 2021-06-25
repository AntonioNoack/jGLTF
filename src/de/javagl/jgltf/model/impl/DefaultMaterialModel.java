package de.javagl.jgltf.model.impl;

import de.javagl.jgltf.model.MaterialModel;
import de.javagl.jgltf.model.gl.TechniqueModel;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implementation of a {@link MaterialModel}
 */
public final class DefaultMaterialModel extends AbstractNamedModelElement
        implements MaterialModel {
    /**
     * The {@link TechniqueModel}
     */
    private TechniqueModel techniqueModel;

    /**
     * The material parameter values
     */
    private Map<String, Object> values;

    /**
     * Creates a new instance
     */
    public DefaultMaterialModel() {
        this.values = Collections.emptyMap();
    }

    /**
     * Set the material parameter values to be an unmodifiable shallow
     * copy of the given map (or the empty map if the given map is
     * <code>null</code>)
     *
     * @param values The material parameter values
     */
    public void setValues(Map<String, Object> values) {
        if (values == null) {
            this.values = Collections.emptyMap();
        } else {
            this.values = Collections.unmodifiableMap(
                    new LinkedHashMap<>(values));
        }
    }

    /**
     * Set the {@link TechniqueModel}
     *
     * @param techniqueModel The {@link TechniqueModel}
     */
    public void setTechniqueModel(TechniqueModel techniqueModel) {
        this.techniqueModel = techniqueModel;
    }

    @Override
    public TechniqueModel getTechniqueModel() {
        return techniqueModel;
    }

    @Override
    public Map<String, Object> getValues() {
        return values;
    }

}