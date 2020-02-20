package listeners.cost_analysis;

import data_structures.Resource;
import ui_components.MaterialPane;
import utilities.CostAnalyzer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UpdatePriceListener implements ActionListener, PropertyChangeListener {
    private JFormattedTextField numberOfMaterial;
    private JComboBox<Resource> specificMaterial;
    private JFormattedTextField priceOfMaterial;
    private CostAnalyzer analyzer;

    public UpdatePriceListener(MaterialPane pane, CostAnalyzer analyzer) {
        this.analyzer = analyzer;
        this.numberOfMaterial = pane.getNumberOfMaterial();
        this.specificMaterial = pane.getSpecificMaterial();
        this.priceOfMaterial = pane.getPriceOfMaterial();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        calculatePrice();
    }

    private void calculatePrice() {
        int number = (int) numberOfMaterial.getValue();
        Resource resource = (Resource) specificMaterial.getSelectedItem();
        priceOfMaterial.setValue(analyzer.calculateSingleCostFor(number, resource));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        calculatePrice();
    }
}
