package de.javagl.jgltf.model.impl;

import de.javagl.jgltf.impl.v1.Camera;
import de.javagl.jgltf.impl.v1.Node;
import de.javagl.jgltf.model.CameraModel;
import de.javagl.jgltf.model.NodeModel;
import de.javagl.jgltf.model.Suppliers;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Implementation of a {@link CameraModel}
 */
public final class DefaultCameraModel extends AbstractNamedModelElement
        implements CameraModel {
    /**
     * The name of this camera model, suitable to be shown to a user
     */
    private String instanceName;

    /**
     * The {@link NodeModel} of the {@link Node} that the {@link Camera}
     * is attached to
     */
    private NodeModel nodeModel;

    /**
     * The function that computes the view matrix
     */
    private final Function<float[], float[]> viewMatrixComputer;

    /**
     * The function that computes the projection matrix
     */
    private final BiFunction<float[], Float, float[]> projectionMatrixComputer;

    /**
     * Creates a new instance
     *
     * @param viewMatrixComputer       The function that computes the view matrix
     * @param projectionMatrixComputer The function that computes the
     *                                 projection matrix
     */
    public DefaultCameraModel(
            Function<float[], float[]> viewMatrixComputer,
            BiFunction<float[], Float, float[]> projectionMatrixComputer) {
        this.viewMatrixComputer =
                Objects.requireNonNull(viewMatrixComputer,
                        "The viewMatrixComputer may not be null");
        this.projectionMatrixComputer =
                Objects.requireNonNull(projectionMatrixComputer,
                        "The projectionMatrixComputer may not be null");
    }


    /**
     * Set the name of this camera model, suitable to be shown to a user
     *
     * @param instanceName The instance name
     */
    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    /**
     * Set the {@link NodeModel} that the camera is attached to
     *
     * @param nodeModel The {@link NodeModel} that the camera is attached to
     */
    public void setNodeModel(NodeModel nodeModel) {
        this.nodeModel = nodeModel;
    }


    @Override
    public String getInstanceName() {
        return instanceName;
    }

    @Override
    public NodeModel getNodeModel() {
        return nodeModel;
    }

    @Override
    public float[] computeViewMatrix(float[] result) {
        return viewMatrixComputer.apply(result);
    }

    @Override
    public float[] computeProjectionMatrix(float[] result, Float aspectRatio) {
        return projectionMatrixComputer.apply(result, aspectRatio);
    }

    @Override
    public Supplier<float[]> createViewMatrixSupplier() {
        return Suppliers.createTransformSupplier(this,
                CameraModel::computeViewMatrix);
    }

    @Override
    public Supplier<float[]> createProjectionMatrixSupplier(
            DoubleSupplier aspectRatioSupplier) {
        return Suppliers.createTransformSupplier(this, (c, t) ->
        {
            Float aspectRatio = null;
            if (aspectRatioSupplier != null) {
                aspectRatio = (float) aspectRatioSupplier.getAsDouble();
            }
            computeProjectionMatrix(t, aspectRatio);
        });
    }


}
