package de.javagl.jgltf.examples;

import de.javagl.jgltf.model.GltfModel;
import de.javagl.jgltf.model.io.GltfModelReader;
import de.javagl.jgltf.viewer.GltfViewer;
import de.javagl.jgltf.viewer.lwjgl.GltfViewerLwjgl;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GltfViewerMiniDemo {

    public static void main(String[] args) throws Exception {
        GltfModelReader r = new GltfModelReader();
        GltfModel gltfModel = r.read(new File("C:/Users/Antonio/Downloads/SimpleSkin.gltf").toURI());
        SwingUtilities.invokeLater(() -> createAndShowGui(gltfModel));
    }

    private static void createAndShowGui(GltfModel gltfModel) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a viewer based on JOGL or LWJGL:
        // GltfViewer<Component> gltfViewer = new GltfViewerJogl();
        GltfViewer<Component> gltfViewer = new GltfViewerLwjgl();

        gltfViewer.addGltfModel(gltfModel);
        f.getContentPane().add(gltfViewer.getRenderComponent());
        f.setSize(500, 500);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

}