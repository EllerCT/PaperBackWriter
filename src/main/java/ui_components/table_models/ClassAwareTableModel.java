package ui_components.table_models;

import javax.swing.table.DefaultTableModel;

public class ClassAwareTableModel extends DefaultTableModel {

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (getDataVector().get(0).get(columnIndex) == null) {
            return super.getColumnClass(columnIndex);
        } else {
            return getDataVector().get(0).get(columnIndex).getClass();
        }
    }
}
