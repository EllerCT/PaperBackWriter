package listeners.general;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class RemoveRowListener implements ActionListener {

    private JTable table;

    public RemoveRowListener(JTable table) {
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION) == 0) {
            int[] rows = table.getSelectedRows();
            Arrays.sort(rows); // So the index shrinks from last first.
            for (int i = rows.length - 1; i >= 0; i--) {
                ((DefaultTableModel) table.getModel()).removeRow(i);
            }
        }
    }
}