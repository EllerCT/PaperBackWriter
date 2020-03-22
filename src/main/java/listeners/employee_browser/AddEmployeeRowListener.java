package listeners.employee_browser;

import data_structures.PinNumber;
import ui_components.table_models.EmployeeTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class AddEmployeeRowListener implements ActionListener {

    private JTable table;

    public AddEmployeeRowListener(JTable table) {
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultTableModel tableModel = (EmployeeTableModel) table.getModel();
        String now = LocalDateTime.now().toString();
        tableModel.addRow(new Object[]{new PinNumber("0000"), "", "0:0", "0:0", 0, now, now});
    }
}
