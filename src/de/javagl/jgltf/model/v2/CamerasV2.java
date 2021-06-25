package de.javagl.jgltf.model.v2;

import de.javagl.jgltf.impl.v2.Camera;
import de.javagl.jgltf.impl.v2.CameraOrthographic;
import de.javagl.jgltf.impl.v2.CameraPerspective;
import de.javagl.jgltf.model.MathUtils;
import de.javagl.jgltf.model.Utils;

import de.javagl.jgltf.logging.Logger;

/**
 * Utility methods related to cameras in glTF 2.0
 */
class CamerasV2 {
    // TODO This is the same as CamerasV2, except for the package names
    // names of the classes that it operates on. 

    /**
     * The logger used in this class
     */
    private static final Logger logger =
            Logger.getLogger(CamerasV2.class);

    /**
     * Compute the projection matrix for the given {@link Camera}. If the
     * {@link Camera#getType()} is neither <code>"perspective"</code>
     * nor <code>"orthographic"</code>, then an error message will be
     * printed, and the result will be the identity matrix.<br>
     * <br>
     * The result will be written to the given array, as a 4x4 matrix in
     * column major order. If the given array is <code>null</code> or does
     * not have a length of 16, then a new array with length 16 will be
     * created and returned.
     *
     * @param camera      The {@link Camera}
     * @param aspectRatio An optional aspect ratio to use. If this is
     *                    <code>null</code>, then the aspect ratio of the camera will be used.
     * @param result      The array storing the result
     * @return The result array
     */
    static float[] computeProjectionMatrix(
            Camera camera, Float aspectRatio, float[] result) {
        float[] localResult = Utils.validate(result, 16);
        String cameraType = camera.getType();
        if ("perspective".equals(cameraType)) {
            CameraPerspective cameraPerspective = camera.getPerspective();
            float fovRad = cameraPerspective.getYfov();
            float fovDeg = (float) Math.toDegrees(fovRad);
            float localAspectRatio = 1.0f;
            if (aspectRatio != null) {
                localAspectRatio = aspectRatio;
            } else if (cameraPerspective.getAspectRatio() != null) {
                localAspectRatio = cameraPerspective.getAspectRatio();
            }
            float zNear = cameraPerspective.getZnear();
            Float zFar = cameraPerspective.getZfar();
            if (zFar == null) {
                MathUtils.infinitePerspective4x4(
                        fovDeg, localAspectRatio, zNear, localResult);
            } else {
                MathUtils.perspective4x4(
                        fovDeg, localAspectRatio, zNear, zFar, localResult);
            }
        } else if ("orthographic".equals(cameraType)) {
            CameraOrthographic cameraOrthographic =
                    camera.getOrthographic();
            float xMag = cameraOrthographic.getXmag();
            float yMag = cameraOrthographic.getYmag();
            float zNear = cameraOrthographic.getZnear();
            float zFar = cameraOrthographic.getZfar();
            MathUtils.setIdentity4x4(localResult);
            localResult[0] = 1.0f / xMag;
            localResult[5] = 1.0f / yMag;
            localResult[10] = 2.0f / (zNear - zFar);
            localResult[14] = (zFar + zNear) / (zNear - zFar);
        } else {
            logger.severe("Invalid camera type: " + cameraType);
            MathUtils.setIdentity4x4(localResult);
        }
        return localResult;
    }

    /**
     * Private constructor to prevent instantiation
     */
    private CamerasV2() {
        // Private constructor to prevent instantiation
    }
}