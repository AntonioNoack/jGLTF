package de.javagl.jgltf.model.impl;

import de.javagl.jgltf.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of a {@link SkinModel}
 */
public final class DefaultSkinModel extends AbstractNamedModelElement
        implements SkinModel {
    /**
     * The bind shape matrix
     */
    private final float[] bindShapeMatrix;

    /**
     * The joint nodes
     */
    private final List<NodeModel> joints;

    /**
     * The skeleton node
     */
    private NodeModel skeleton;

    /**
     * The inverse bind matrices
     */
    private AccessorModel inverseBindMatrices;

    /**
     * Creates a new instance
     *
     * @param bindShapeMatrix The bind shape matrix. A copy of this array
     *                        will be stored. If it is <code>null</code>, a new array will be
     *                        created, which represents the identity matrix.
     */
    public DefaultSkinModel(float[] bindShapeMatrix) {
        if (bindShapeMatrix == null) {
            this.bindShapeMatrix = MathUtils.createIdentity4x4();
        } else {
            this.bindShapeMatrix = bindShapeMatrix.clone();
        }
        this.joints = new ArrayList<>();
    }

    /**
     * Add the given joint
     *
     * @param joint The joint
     */
    public void addJoint(NodeModel joint) {
        Objects.requireNonNull(joint, "The joint may not be null");
        joints.add(joint);
    }

    /**
     * Set the skeleton root node
     *
     * @param skeleton The skeleton root node
     */
    public void setSkeleton(NodeModel skeleton) {
        this.skeleton = skeleton;
    }

    /**
     * Set the inverse bind matrices
     *
     * @param inverseBindMatrices The inverse bind matrices
     */
    public void setInverseBindMatrices(AccessorModel inverseBindMatrices) {
        this.inverseBindMatrices = Objects.requireNonNull(
                inverseBindMatrices, "The inverseBindMatrices may not be null");
    }


    @Override
    public float[] getBindShapeMatrix(float[] result) {
        float[] localResult = Utils.validate(result, 16);
        System.arraycopy(bindShapeMatrix, 0, localResult, 0, 16);
        return localResult;
    }


    @Override
    public List<NodeModel> getJoints() {
        return Collections.unmodifiableList(joints);
    }

    @Override
    public NodeModel getSkeleton() {
        return skeleton;
    }

    @Override
    public AccessorModel getInverseBindMatrices() {
        return inverseBindMatrices;
    }

    @Override
    public float[] getInverseBindMatrix(int index, float[] result) {
        float[] localResult = Utils.validate(result, 16);
        AccessorFloatData inverseBindMatricesData =
                AccessorDatas.createFloat(inverseBindMatrices);
        for (int j = 0; j < 16; j++) {
            localResult[j] = inverseBindMatricesData.get(index, j);
        }
        return localResult;
    }

}
