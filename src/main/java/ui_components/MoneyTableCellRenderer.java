package ui_components;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;

public class MoneyTableCellRenderer extends DefaultTableCellRenderer {
    private final DecimalFormat formatter = new DecimalFormat("$0.00");

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        value = formatter.format(value);
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
