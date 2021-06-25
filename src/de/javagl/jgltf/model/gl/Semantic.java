package de.javagl.jgltf.model.gl;

/**
 * Enumeration of the {@link TechniqueParametersModel#getSemantic() technique
 * parameters semantics}
 */
public enum Semantic {
    /**
     * The LOCAL semantic
     */
    LOCAL,

    /**
     * The MODEL semantic
     */
    MODEL,

    /**
     * The VIEW semantic
     */
    VIEW,

    /**
     * The PROJECTION semantic
     */
    PROJECTION,

    /**
     * The MODELVIEW semantic
     */
    MODELVIEW,

    /**
     * The MODELVIEWPROJECTION semantic
     */
    MODELVIEWPROJECTION,

    /**
     * The MODELINVERSE semantic
     */
    MODELINVERSE,

    /**
     * The VIEWINVERSE semantic
     */
    VIEWINVERSE,

    /**
     * The MODELVIEWINVERSE semantic
     */
    MODELVIEWINVERSE,

    /**
     * The PROJECTIONINVERSE semantic
     */
    PROJECTIONINVERSE,

    /**
     * The MODELVIEWPROJECTIONINVERSE semantic
     */
    MODELVIEWPROJECTIONINVERSE,

    /**
     * The MODELINVERSETRANSPOSE semantic
     */
    MODELINVERSETRANSPOSE,

    /**
     * The MODELVIEWINVERSETRANSPOSE semantic
     */
    MODELVIEWINVERSETRANSPOSE,

    /**
     * The VIEWPORT semantic
     */
    VIEWPORT,

    /**
     * The JOINTMATRIX semantic
     */
    JOINTMATRIX;

    /**
     * Returns whether the given string is a valid semantic name, and may be
     * passed to <code>Semantic.valueOf</code> without causing an exception.
     *
     * @param s The string
     * @return Whether the given string is a valid semantic
     */
    public static boolean contains(String s) {
        for (Semantic semantic : values()) {
            if (semantic.name().equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the semantic for the given string. If the string is
     * <code>null</code> or does not describe a valid semantic,
     * then <code>null</code> is returned
     *
     * @param string The string
     * @return The semantic
     */
    public static Semantic forString(String string) {
        if (string == null) {
            return null;
        }
        if (!contains(string)) {
            return null;
        }
        return Semantic.valueOf(string);
    }

}
