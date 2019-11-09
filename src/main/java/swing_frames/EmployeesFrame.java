package swing_frames;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeesFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable employeeTable;

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

    public JPanel getPanel(){
        return contentPane;
    }
}
