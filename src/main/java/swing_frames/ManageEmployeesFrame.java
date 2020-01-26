package swing_frames;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;

public class ManageEmployeesFrame extends JFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable employeeTable;
    private JButton newRow;
    private JButton removeRow;

    public void setNewRowAction(ActionListener action) {
        newRow.addActionListener(action);
    }

    public void setRemoveRowAction(ActionListener action) {
        removeRow.addActionListener(action);
    }

    public void setOKAction(ActionListener action){
        buttonOK.addActionListener(action);
    }

    public void setCancelAction(ActionListener action){
        buttonCancel.addActionListener(action);
    }

    public void setTableModel(TableModel model){
        employeeTable.setModel(model);
    }

    public TableModel getModel(){
        return employeeTable.getModel();
    }

    public JTable getTable() {
        return employeeTable;
    }

    public JPanel getPanel(){
        return contentPane;
    }
}
