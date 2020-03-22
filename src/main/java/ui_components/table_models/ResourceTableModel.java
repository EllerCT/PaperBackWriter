package ui_components.table_models;

import data_structures.Resource;

import java.util.Map;
import java.util.Vector;

public class ResourceTableModel extends ClassAwareTableModel {

    public ResourceTableModel(Map data) {
        addColumn("Type");
        addColumn("Name");
        addColumn("Unit Size");
        addColumn("Price Per Unit");

        for (Object value : data.values()) {
            Resource resource = (Resource) value;
            Vector<Object> newRow = new Vector<>();
            newRow.add(resource.getType());
            newRow.add(resource.getName());
            newRow.add(resource.getUnitSize());
            newRow.add(resource.getPricePerUnit());
            addRow(newRow);
        }
    }
}
