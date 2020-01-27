package listeners.event_browser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEventRowListener implements ActionListener {
    private JTable table;

    public AddEventRowListener(JTable table) {
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.addRow(new String[]{"NewCode", "", "0", "", ""});
    }
}
