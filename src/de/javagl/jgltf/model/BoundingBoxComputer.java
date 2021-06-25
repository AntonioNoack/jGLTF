package de.javagl.jgltf.model;

import java.util.List;
import java.util.Map;
import de.javagl.jgltf.logging.Logger;

/**
 * #ANNO no reason for private stuff
 * A (package-private!) utility class to compute bounding volumes
 */
public class BoundingBoxComputer {
    /**
     * The logger used in this class
     */
    private static final Logger logger =
            Logger.getLogger(BoundingBoxComputer.class);

    /**
     * The {@link GltfModel}
     */
    private final GltfModel gltfModel;

    /**
     * Create a new bounding box computer for the given {@link GltfModel}
     *
     * @param gltfModel The {@link GltfModel}
     */
    BoundingBoxComputer(GltfModel gltfModel) {
        this.gltfModel = gltfModel;
    }

    /**
     * Compute the bounding box of the {@link GltfModel}
     *
     * @return The bounding box
     */
    BoundingBox compute() {
        BoundingBox boundingBox = new BoundingBox();
        List<SceneModel> sceneModels = gltfModel.getSceneModels();
        for (SceneModel sceneModel : sceneModels) {
            float[] rootTransform = MathUtils.createIdentity4x4();
            computeSceneBoundingBox(sceneModel, rootTransform, boundingBox);
        }
        return boundingBox;
    }

    /**
     * Recursively compute the bounding box of the {@link MeshPrimitiveModel}
     * objects of all {@link MeshModel} objects in the given {@link SceneModel}
     * (including the respective global node transforms).
     * If the given result is <code>null</code>, then a new bounding box
     * will be created and returned.
     *
     * @param sceneModel  The {@link SceneModel}
     * @param transform   The root transform, as a column major 4x4 matrix
     * @param boundingBox The optional bounding box that will store the result
     * @return The result
     */
    private BoundingBox computeSceneBoundingBox(
            SceneModel sceneModel, float[] transform, BoundingBox boundingBox) {
        BoundingBox localResult = boundingBox;
        if (localResult == null) {
            localResult = new BoundingBox();
        }
        List<NodeModel> nodeModels = sceneModel.getNodeModels();
        for (NodeModel nodeModel : nodeModels) {
            computeNodeBoundingBox(nodeModel, transform, localResult);
        }
        return localResult;
    }


    /**
     * Recursively compute the bounding box of the {@link MeshPrimitiveModel}
     * objects of all {@link MeshModel} objects in the given {@link NodeModel}
     * and its children (including the respective global node transforms).
     * If the given result is <code>null</code>, then a new bounding box
     * will be created and returned.
     *
     * @param nodeModel       The {@link NodeModel}
     * @param parentTransform The transform, as a column major 4x4 matrix
     * @param boundingBox     The optional bounding box that will store the result
     * @return The result
     */
    private BoundingBox computeNodeBoundingBox(
            NodeModel nodeModel, float[] parentTransform, BoundingBox boundingBox) {
        BoundingBox result = boundingBox;
        if (result == null) {
            result = new BoundingBox();
        }

        float[] localTransform = nodeModel.computeLocalTransform(null);
        float[] transform = new float[16];
        MathUtils.mul4x4(parentTransform, localTransform, transform);

        List<MeshModel> meshModels = nodeModel.getMeshModels();
        for (MeshModel meshModel : meshModels) {
            BoundingBox meshBoundingBox =
                    computeMeshBoundingBox(
                            meshModel, transform, result);
            result.combine(meshBoundingBox);
        }

        List<NodeModel> children = nodeModel.getChildren();
        for (NodeModel child : children) {
            computeNodeBoundingBox(child, transform, result);
        }
        return result;
    }

    /**
     * Compute the bounding box of the given {@link MeshModel}, under
     * the given transform.
     * If the given result is <code>null</code>, then a new bounding box
     * will be created and returned.
     *
     * @param meshModel   The {@link MeshModel}
     * @param transform   The optional transform. If this is <code>null</code>,
     *                    then the identity matrix will be assumed.
     * @param boundingBox The optional bounding box that will store the result
     * @return The result
     */
    private BoundingBox computeMeshBoundingBox(
            MeshModel meshModel, float[] transform, BoundingBox boundingBox) {
        BoundingBox result = boundingBox;
        if (result == null) {
            result = new BoundingBox();
        }

        List<MeshPrimitiveModel> primitives =
                meshModel.getMeshPrimitiveModels();
        for (MeshPrimitiveModel meshPrimitiveModel : primitives) {
            BoundingBox meshPrimitiveBoundingBox =
                    computeBoundingBox(meshPrimitiveModel, transform);
            if (meshPrimitiveBoundingBox != null) {
                result.combine(meshPrimitiveBoundingBox);
            }
        }
        return result;
    }

    /**
     * Compute the bounding box of the given {@link MeshPrimitiveModel}, under
     * the given transform.
     *
     * @param meshPrimitiveModel The {@link MeshPrimitiveModel}
     * @param transform          The optional transform. If this is <code>null</code>,
     *                           then the identity matrix will be assumed.
     * @return The {@link BoundingBox}, or <code>null</code> if the given
     * {@link MeshPrimitiveModel} does not refer to an {@link AccessorModel}
     * with its <code>"POSITION"</code> attribute. If if refers to
     * an {@link AccessorModel} that does not contain 3D float elements,
     * then a warning will be printed and <code>null</code> will be
     * returned.
     */
    private BoundingBox computeBoundingBox(
            MeshPrimitiveModel meshPrimitiveModel, float[] transform) {
        Map<String, AccessorModel> attributes =
                meshPrimitiveModel.getAttributes();
        String positionsAttributeName = "POSITION";
        AccessorModel accessorModel = attributes.get(positionsAttributeName);
        if (accessorModel == null) {
            return null;
        }

        ElementType accessorType = accessorModel.getElementType();
        int numComponents = accessorType.getNumComponents();
        if (numComponents < 3) {
            logger.warning("Mesh primitive " + positionsAttributeName +
                    " attribute refers to an accessor with type " + accessorType +
                    " - expected \"VEC3\" or \"VEC4\"");
            return null;
        }
        Class<?> componentDataType = accessorModel.getComponentDataType();
        if (!componentDataType.equals(float.class)) {
            logger.warning("Mesh primitive " + positionsAttributeName +
                    " attribute refers to an accessor with component type " +
                    GltfConstants.stringFor(accessorModel.getComponentType()) +
                    " - expected GL_FLOAT");
        }

        AccessorData accessorData = accessorModel.getAccessorData();
        AccessorFloatData accessorFloatData = (AccessorFloatData) accessorData;

        float[] point = new float[3];
        float[] transformedPoint;
        if (transform != null) {
            transformedPoint = new float[3];
        } else {
            transformedPoint = point;
        }

        BoundingBox boundingBox = new BoundingBox();
        for (int e = 0; e < accessorData.getNumElements(); e++) {
            for (int c = 0; c < 3; c++) {
                point[c] = accessorFloatData.get(e, c);
            }
            if (transform != null) {
                MathUtils.transformPoint3D(transform, point, transformedPoint);
            }
            boundingBox.combine(
                    transformedPoint[0],
                    transformedPoint[1],
                    transformedPoint[2]);
        }
        return boundingBox;
    }

}
