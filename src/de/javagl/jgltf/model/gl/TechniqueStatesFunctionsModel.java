package de.javagl.jgltf.model.gl;

/**
 * Interface for technique state function parameters. <br>
 * <br>
 * <b>Note: The method comments here are placeholders. For details, refer
 * to the glTF 1.0 specification and an OpenGL documentation!</b><br>
 * <br>
 * Note: The methods in this interface may return <i>references</i>
 * to arrays that are stored internally. Callers should not store
 * or modify the returned arrays!
 */
public interface TechniqueStatesFunctionsModel {
    /**
     * Returns the blend color
     *
     * @return The blend color
     */
    float[] getBlendColor();

    /**
     * Returns the blend equation
     *
     * @return the blend equation
     */
    int[] getBlendEquationSeparate();

    /**
     * Returns the blend function
     *
     * @return The blend function
     */
    int[] getBlendFuncSeparate();

    /**
     * Returns the color mask
     *
     * @return The color mask
     */
    boolean[] getColorMask();

    /**
     * Returns the cull face
     *
     * @return The cull face
     */
    int[] getCullFace();

    /**
     * Returns the depth func
     *
     * @return The depth func
     */
    int[] getDepthFunc();

    /**
     * Returns the depth mask
     *
     * @return The depth mask
     */
    boolean[] getDepthMask();

    /**
     * Returns the depth range
     *
     * @return The depth range
     */
    float[] getDepthRange();

    /**
     * Returns the front face
     *
     * @return The front face
     */
    int[] getFrontFace();

    /**
     * Returns the line width
     *
     * @return The line width
     */
    float[] getLineWidth();

    /**
     * Returns the polygon offset
     *
     * @return The polygon offset
     */
    float[] getPolygonOffset();
}
