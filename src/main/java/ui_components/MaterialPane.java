package ui_components;

import data_structures.Material;
import data_structures.Resource;
import data_structures.ResourceType;
import listeners.cost_analysis.UpdatePriceListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MaterialPane extends JPanel {
    private final int PREFERRED_HEIGHT = 50;

    private JComboBox<ResourceType> typeOfMaterial;
    private JComboBox<Resource> specificMaterial;
    private JFormattedTextField numberOfMaterial;
    private JFormattedTextField priceOfMaterial;
    private JButton removeMaterial;

    public MaterialPane() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setPreferredSize(new Dimension(500, PREFERRED_HEIGHT));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, PREFERRED_HEIGHT));
        typeOfMaterial = new JComboBox<>(ResourceType.values());
        configureTypeOfMaterialBox();
        specificMaterial = new JComboBox<>();
        configureSpecificMaterialBox();
        numberOfMaterial = new JFormattedTextField();
        configureNumberOfMaterialField();
        priceOfMaterial = new JFormattedTextField();
        configurePriceOfMaterial();
        removeMaterial = new JButton("Remove");
        configureRemoveMaterial();
        this.add(typeOfMaterial);
        this.add(specificMaterial);
        this.add(numberOfMaterial);
        this.add(priceOfMaterial);
        this.add(removeMaterial);
    }

    public JComboBox<ResourceType> getTypeOfMaterial() {
        return typeOfMaterial;
    }

    public JComboBox<Resource> getSpecificMaterial() {
        return specificMaterial;
    }

    public JFormattedTextField getNumberOfMaterial() {
        return numberOfMaterial;
    }

    public JFormattedTextField getPriceOfMaterial() {
        return priceOfMaterial;
    }

    public void setMaterialTypeChangedAction(ActionListener listener) {
        typeOfMaterial.addActionListener(listener);
        typeOfMaterial.setSelectedIndex(0);
    }

    public void setUpdatePriceAction(UpdatePriceListener listener) {
        specificMaterial.addActionListener(listener);
        numberOfMaterial.addPropertyChangeListener("value", listener);
    }

    private void configureTypeOfMaterialBox() {
        typeOfMaterial.setPreferredSize(new Dimension(100, PREFERRED_HEIGHT));
    }

    private void configureSpecificMaterialBox() {
        specificMaterial.setPreferredSize(new Dimension(150, PREFERRED_HEIGHT));
        specificMaterial.addItem(new Resource("None"));

    }

    private void configureNumberOfMaterialField() {
        numberOfMaterial.setPreferredSize(new Dimension(60, PREFERRED_HEIGHT));
        numberOfMaterial.setMaximumSize(new Dimension(80, PREFERRED_HEIGHT));
        numberOfMaterial.setHorizontalAlignment(JTextField.CENTER);
        numberOfMaterial.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
        // Format the text field to Integers via Integer.valueOf
        numberOfMaterial.setValue(Integer.valueOf(0));
    }

    private void configurePriceOfMaterial() {
        priceOfMaterial.setPreferredSize(new Dimension(100, PREFERRED_HEIGHT));
        priceOfMaterial.setHorizontalAlignment(JTextField.RIGHT);
        priceOfMaterial.setEditable(false);
    }

    private void configureRemoveMaterial() {
        removeMaterial.setPreferredSize(new Dimension(150, 40));
        // Tightly coupled, but extracting it adds an order of magnitude
        // more complexity that is not needed at this time.
        removeMaterial.addActionListener(e -> {
            Container parent = this.getParent();
            parent.remove(this);
            parent.revalidate();
            parent.repaint();
        });
    }

    // TODO: Figure out how to generify this to a Controller


    public Material getMaterial() {
        if (!specificMaterial.getSelectedItem().equals(new Resource("None"))) {
            return null;
        } else {
            Material material = new Material(
                    (ResourceType) typeOfMaterial.getSelectedItem(),
                    (Resource) specificMaterial.getSelectedItem(),
                    (Integer) numberOfMaterial.getValue());
            material.setCost((Double) priceOfMaterial.getValue());
            return material;
        }
    }


}
