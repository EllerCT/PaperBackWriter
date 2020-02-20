package ui_components;

import data_structures.Material;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class ReadOnlyMaterialPane extends JPanel {
    private final int PREFERRED_HEIGHT = 50;

    private Material baseMaterial;
    private JTextField typeOfMaterial;
    private JTextField specificMaterial;
    private JFormattedTextField numberOfMaterial;
    private JFormattedTextField priceOfMaterial;

    public ReadOnlyMaterialPane(Material toReadFrom) {
        configureThis();
        this.baseMaterial = toReadFrom;
        this.typeOfMaterial = new JTextField(baseMaterial.getResourceType());
        configureTypeOfMaterialField();
        this.specificMaterial = new JTextField(baseMaterial.getSpecific());
        configureSpecificMaterialField();
        this.numberOfMaterial = new JFormattedTextField(baseMaterial.getNumber());
        configureNumberOfMaterialField();
        this.priceOfMaterial = new JFormattedTextField(baseMaterial.getCost());
        configurePriceOfMaterial();
        addComponents();
    }

    public Material getMaterial() {
        return baseMaterial;
    }

    private void configureThis() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setPreferredSize(new Dimension(500, PREFERRED_HEIGHT));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, PREFERRED_HEIGHT));
    }

    private void addComponents() {
        this.add(typeOfMaterial);
        this.add(specificMaterial);
        this.add(numberOfMaterial);
        this.add(priceOfMaterial);
    }

    private void configureTypeOfMaterialField() {
        typeOfMaterial.setPreferredSize(new Dimension(100, PREFERRED_HEIGHT));
        typeOfMaterial.setEditable(false);
    }

    private void configureSpecificMaterialField() {
        specificMaterial.setPreferredSize(new Dimension(150, PREFERRED_HEIGHT));
        specificMaterial.setEditable(false);
    }

    private void configureNumberOfMaterialField() {
        numberOfMaterial.setMinimumSize(new Dimension(30, PREFERRED_HEIGHT));
        numberOfMaterial.setPreferredSize(new Dimension(60, PREFERRED_HEIGHT));
        numberOfMaterial.setMaximumSize(new Dimension(80, PREFERRED_HEIGHT));
        numberOfMaterial.setHorizontalAlignment(JTextField.CENTER);
        numberOfMaterial.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);

        numberOfMaterial.setEditable(false);
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
}
