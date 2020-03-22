package ui_components.table_models;

import data_structures.Resource;
import io_pipes.ResourceIOPipe;

import java.util.Map;
import java.util.Vector;

public class ResourceTableModel extends ClassAwareTableModel {

    public ResourceTableModel(Map data) {
        for (String header : ResourceIOPipe.CSV_FORMAT.getHeader()) {
            addColumn(header);
        }
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
