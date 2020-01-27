package listeners.resource_browser;

import data_structures.ResourceType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddResourceRowListener implements ActionListener {

    private JTable table;

    public AddResourceRowListener(JTable table) {
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.addRow(new Object[]{ResourceType.PAPER, "", "", 0.0, 0});
    }
}
