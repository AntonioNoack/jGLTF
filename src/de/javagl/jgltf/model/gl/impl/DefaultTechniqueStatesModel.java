package de.javagl.jgltf.model.gl.impl;

import de.javagl.jgltf.model.gl.TechniqueStatesFunctionsModel;
import de.javagl.jgltf.model.gl.TechniqueStatesModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of a {@link TechniqueStatesModel}
 */
public class DefaultTechniqueStatesModel implements TechniqueStatesModel {
    /**
     * The enabled states
     */
    private final List<Integer> enable;

    /**
     * The {@link TechniqueStatesFunctionsModel}
     */
    private final TechniqueStatesFunctionsModel techniqueStatesFunctionsModel;

    /**
     * Default constructor
     *
     * @param enable                        The enabled states
     * @param techniqueStatesFunctionsModel The {@link TechniqueStatesFunctionsModel}
     */
    public DefaultTechniqueStatesModel(List<Integer> enable,
                                       TechniqueStatesFunctionsModel techniqueStatesFunctionsModel) {
        this.enable = Collections.unmodifiableList(
                new ArrayList<>(enable));
        this.techniqueStatesFunctionsModel = techniqueStatesFunctionsModel;
    }

    @Override
    public List<Integer> getEnable() {
        return enable;
    }

    @Override
    public TechniqueStatesFunctionsModel getTechniqueStatesFunctionsModel() {
        return techniqueStatesFunctionsModel;
    }
}
