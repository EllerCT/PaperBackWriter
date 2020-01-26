package swing_frames;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;

public class ResourcesFrame extends JFrame {
    private JPanel contentPane;
    private JTable resourceTable;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton addButton;
    private JButton removeButton;

    public JPanel getPanel() {
        return this.contentPane;
    }

    public void setSaveButtonListener(ActionListener action) {
        saveButton.addActionListener(action);
    }

    public void setCancelButtonListener(ActionListener action) {
        cancelButton.addActionListener(action);
    }

    public void setAddButtonListener(ActionListener action) {
        addButton.addActionListener(action);
    }

    public void setRemoveButtonListener(ActionListener action) {
        removeButton.addActionListener(action);
    }

    public void setTableModel(TableModel model) {
        resourceTable.setModel(model);
    }

    public TableModel getModel() {
        return resourceTable.getModel();
    }

    public JTable getTable() {
        return resourceTable;
    }


}
