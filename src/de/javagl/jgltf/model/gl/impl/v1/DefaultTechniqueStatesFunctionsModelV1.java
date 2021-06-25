package de.javagl.jgltf.model.gl.impl.v1;

import de.javagl.jgltf.impl.v1.TechniqueStatesFunctions;
import de.javagl.jgltf.model.Optionals;
import de.javagl.jgltf.model.gl.TechniqueStatesFunctionsModel;

import java.util.Objects;

/**
 * Default implementation of a {@link TechniqueStatesFunctionsModel},
 * based on glTF 1.0 {@link TechniqueStatesFunctions}
 */
public class DefaultTechniqueStatesFunctionsModelV1
        implements TechniqueStatesFunctionsModel {
    /**
     * The {@link TechniqueStatesFunctions}
     */
    private final TechniqueStatesFunctions functions;

    /**
     * Default constructor
     *
     * @param functions The {@link TechniqueStatesFunctions}
     */
    public DefaultTechniqueStatesFunctionsModelV1(
            TechniqueStatesFunctions functions) {
        this.functions = Objects.requireNonNull(
                functions, "The functions may not be null");
    }

    @Override
    public float[] getBlendColor() {
        return Optionals.of(
                functions.getBlendColor(),
                functions.defaultBlendColor());
    }

    @Override
    public int[] getBlendEquationSeparate() {
        return Optionals.of(
                functions.getBlendEquationSeparate(),
                functions.defaultBlendEquationSeparate());
    }

    @Override
    public int[] getBlendFuncSeparate() {
        return Optionals.of(
                functions.getBlendFuncSeparate(),
                functions.defaultBlendFuncSeparate());
    }

    @Override
    public boolean[] getColorMask() {
        return Optionals.of(
                functions.getColorMask(),
                functions.defaultColorMask());
    }

    @Override
    public int[] getCullFace() {
        return Optionals.of(
                functions.getCullFace(),
                functions.defaultCullFace());
    }

    @Override
    public int[] getDepthFunc() {
        return Optionals.of(
                functions.getDepthFunc(),
                functions.defaultDepthFunc());
    }

    @Override
    public boolean[] getDepthMask() {
        return Optionals.of(
                functions.getDepthMask(),
                functions.defaultDepthMask());
    }

    @Override
    public float[] getDepthRange() {
        return Optionals.of(
                functions.getDepthRange(),
                functions.defaultDepthRange());
    }

    @Override
    public int[] getFrontFace() {
        return Optionals.of(
                functions.getFrontFace(),
                functions.defaultFrontFace());
    }

    @Override
    public float[] getLineWidth() {
        return Optionals.of(
                functions.getLineWidth(),
                functions.defaultLineWidth());
    }

    @Override
    public float[] getPolygonOffset() {
        return Optionals.of(
                functions.getPolygonOffset(),
                functions.defaultPolygonOffset());
    }


}
