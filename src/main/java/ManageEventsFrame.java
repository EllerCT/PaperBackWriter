import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;

public class ManageEventsFrame {
    private JPanel contentPane;
    private JButton confirmButton;
    private JButton cancelButton;
    private JTable eventsTable;

    public void setConfirmAction(ActionListener action){
        confirmButton.addActionListener(action);
    }

    public void setCancelAction(ActionListener action){
        cancelButton.addActionListener(action);
    }

    public void setTableModel(TableModel model){
        eventsTable.setModel(model);
    }

    public TableModel getModel(){
        return eventsTable.getModel();
    }


    public JPanel getPanel() {
        return contentPane;
    }
}
