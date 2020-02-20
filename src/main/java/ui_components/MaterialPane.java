package ui_components;

import data_structures.Material;
import data_structures.Resource;
import data_structures.ResourceType;
import listeners.cost_analysis.UpdatePriceListener;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

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
        // Fire once
        listener.actionPerformed(null);
    }

    public void setUpdatePriceAction(UpdatePriceListener listener) {
        specificMaterial.addActionListener(listener);
        numberOfMaterial.addPropertyChangeListener("value", listener);
        // Fire once
        listener.actionPerformed(null);
    }

    private void configureTypeOfMaterialBox() {
        typeOfMaterial.setPreferredSize(new Dimension(100, PREFERRED_HEIGHT));
    }

    private void configureSpecificMaterialBox() {
        specificMaterial.setPreferredSize(new Dimension(150, PREFERRED_HEIGHT));
        specificMaterial.addItem(new Resource("None"));

    }

    private void configureNumberOfMaterialField() {
        numberOfMaterial.setMinimumSize(new Dimension(30, PREFERRED_HEIGHT));
        numberOfMaterial.setPreferredSize(new Dimension(60, PREFERRED_HEIGHT));
        numberOfMaterial.setMaximumSize(new Dimension(80, PREFERRED_HEIGHT));
        numberOfMaterial.setHorizontalAlignment(JTextField.CENTER);
        numberOfMaterial.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
        // Format the text field to Integers via Integer.valueOf
        numberOfMaterial.setValue(Integer.valueOf(0));
    }

    private void configurePriceOfMaterial() {
        priceOfMaterial.setMinimumSize(new Dimension(40, PREFERRED_HEIGHT));
        priceOfMaterial.setPreferredSize(new Dimension(100, PREFERRED_HEIGHT));
        priceOfMaterial.setMaximumSize(new Dimension(200, PREFERRED_HEIGHT));
        priceOfMaterial.setHorizontalAlignment(JTextField.RIGHT);
        priceOfMaterial.setEditable(false);

        NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();
        moneyFormat.setMaximumFractionDigits(2);
        NumberFormatter formatter = new NumberFormatter(moneyFormat);
        formatter.setAllowsInvalid(false);

        priceOfMaterial.setFormatterFactory(new DefaultFormatterFactory(formatter));
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

    public Double getPrice() {
        return (Double) priceOfMaterial.getValue();
    }

    public Material getMaterial() {
        Resource emptyResource = new Resource("None");
        emptyResource.setType((ResourceType) typeOfMaterial.getSelectedItem());
        if (specificMaterial.getSelectedItem().equals(emptyResource)) {
            return null;
        } else {
            Material material = new Material(
                    (String) typeOfMaterial.getSelectedItem(),
                    (String) specificMaterial.getSelectedItem(),
                    (Integer) numberOfMaterial.getValue());
            material.setCost((Double) priceOfMaterial.getValue());
            return material;
        }
    }


}
