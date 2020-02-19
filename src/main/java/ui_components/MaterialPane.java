package ui_components;

import data_structures.Material;
import data_structures.Resource;
import data_structures.ResourceType;

import javax.swing.*;

public class MaterialPane extends JPanel {
    private JComboBox<ResourceType> typeOfMaterial;
    private JComboBox<Resource> specificMaterial;
    private JFormattedTextField numberOfMaterial;

    public Material getMaterial() {
        return new Material(
                (ResourceType) typeOfMaterial.getSelectedItem(),
                (Resource) specificMaterial.getSelectedItem(),
                Integer.parseInt(numberOfMaterial.getText())
        );
    }
}
