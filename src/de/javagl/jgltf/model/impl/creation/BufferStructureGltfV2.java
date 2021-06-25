package de.javagl.jgltf.model.impl.creation;

import de.javagl.jgltf.impl.v2.Accessor;
import de.javagl.jgltf.impl.v2.Buffer;
import de.javagl.jgltf.impl.v2.BufferView;
import de.javagl.jgltf.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods for creating the glTF 2.0 elements that correspond to
 * a {@link BufferStructure}
 */
public class BufferStructureGltfV2 {
    /**
     * Create the {@link Accessor} objects from the given
     * {@link BufferStructure}
     *
     * @param bufferStructure The {@link BufferStructure}
     * @return The {@link Accessor} objects
     */
    public static List<Accessor> createAccessors(
            BufferStructure bufferStructure) {
        List<BufferViewModel> bufferViewModels =
                bufferStructure.getBufferViewModels();
        List<AccessorModel> accessorModels =
                bufferStructure.getAccessorModels();
        List<Accessor> accessors = new ArrayList<>();
        for (AccessorModel accessorModel : accessorModels) {
            BufferViewModel bufferViewModel =
                    accessorModel.getBufferViewModel();
            int bufferViewIndex = bufferViewModels.indexOf(bufferViewModel);
            accessors.add(createAccessor(accessorModel, bufferViewIndex));
        }
        return accessors;
    }

    /**
     * Create the {@link Accessor} object from the given
     * {@link AccessorModel}
     *
     * @param accessorModel   The {@link AccessorModel}
     * @param bufferViewIndex The {@link BufferView} index
     * @return The {@link Accessor} object
     */
    private static Accessor createAccessor(
            AccessorModel accessorModel, int bufferViewIndex) {
        Accessor accessor = new Accessor();

        accessor.setBufferView(bufferViewIndex);

        accessor.setByteOffset(accessorModel.getByteOffset());
        accessor.setComponentType(accessorModel.getComponentType());
        accessor.setCount(accessorModel.getCount());
        accessor.setType(accessorModel.getElementType().toString());

        AccessorData accessorData = accessorModel.getAccessorData();
        accessor.setMax(AccessorDatas.computeMax(accessorData));
        accessor.setMin(AccessorDatas.computeMin(accessorData));

        return accessor;
    }

    /**
     * Create the {@link BufferView} objects from the given
     * {@link BufferStructure}
     *
     * @param bufferStructure The {@link BufferStructure}
     * @return The {@link BufferView} objects
     */
    public static List<BufferView> createBufferViews(
            BufferStructure bufferStructure) {
        List<BufferModel> bufferModels = bufferStructure.getBufferModels();

        List<BufferViewModel> bufferViewModels =
                bufferStructure.getBufferViewModels();
        List<BufferView> bufferViews = new ArrayList<>();
        for (BufferViewModel bufferViewModel : bufferViewModels) {
            BufferModel bufferModel = bufferViewModel.getBufferModel();
            int bufferIndex = bufferModels.indexOf(bufferModel);
            bufferViews.add(createBufferView(
                    bufferViewModel, bufferIndex));
        }
        return bufferViews;
    }

    /**
     * Create the {@link BufferView} object from the given
     * {@link BufferViewModel}
     *
     * @param bufferViewModel The {@link BufferViewModel}
     * @param bufferIndex     The {@link Buffer} index
     * @return The {@link BufferView} objects
     */
    private static BufferView createBufferView(
            BufferViewModel bufferViewModel, int bufferIndex) {
        BufferView bufferView = new BufferView();

        bufferView.setBuffer(bufferIndex);
        bufferView.setByteOffset(bufferViewModel.getByteOffset());
        bufferView.setByteLength(bufferViewModel.getByteLength());
        bufferView.setByteStride(bufferViewModel.getByteStride());
        bufferView.setTarget(bufferViewModel.getTarget());

        return bufferView;
    }

    /**
     * Create the {@link Buffer} objects from the given
     * {@link BufferStructure}
     *
     * @param bufferStructure The {@link BufferStructure}
     * @return The {@link Buffer} objects
     */
    public static List<Buffer> createBuffers(BufferStructure bufferStructure) {
        List<BufferModel> bufferModels =
                bufferStructure.getBufferModels();
        List<Buffer> buffers = new ArrayList<>();
        for (BufferModel bufferModel : bufferModels) {
            buffers.add(createBuffer(bufferModel));
        }
        return buffers;
    }

    /**
     * Create the {@link Buffer} object from the given {@link BufferModel}
     *
     * @param bufferModel The {@link BufferModel}
     * @return The {@link Buffer}
     */
    private static Buffer createBuffer(BufferModel bufferModel) {
        Buffer buffer = new Buffer();
        buffer.setUri(bufferModel.getUri());
        buffer.setByteLength(bufferModel.getByteLength());
        return buffer;
    }

    /**
     * Private constructor to prevent instantiation
     */
    private BufferStructureGltfV2() {
        // Private constructor to prevent instantiation
    }
}
