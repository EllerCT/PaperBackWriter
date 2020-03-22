package ui_components.table_models;

import data_structures.ModularProduct;

import java.util.Map;

public class ProductTableModel extends ClassAwareTableModel {

    public ProductTableModel(Map productsMap) {
        addColumn("ID");
        addColumn("Name");
        addColumn("Grade");
        addColumn("Total Cost");
        for (Object value : productsMap.values()) {
            ModularProduct product = (ModularProduct) value;
            addRow(new String[]{product.getId(), product.getName(), product.getGrade(), product.getTotalCost()});
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
