package swing_frames;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MainMenuFrame {
    private JButton clockInOutButton;
    private JButton viewEmployeeInformationButton;
    private JButton addNewEmployeeButton;
    private JButton eventClockButton;
    private JButton manageEventsButton;
    private JPanel contentPane;

    public void setTimeClockAction(ActionListener action){
        clockInOutButton.addActionListener(action);
    }

    public void setAttendEventAction(ActionListener action) {
        eventClockButton.addActionListener(action);
    }

    public void setViewEmployeesAction(ActionListener action){
        viewEmployeeInformationButton.addActionListener(action);
    }

    public void setAddNewEmployeeAction(ActionListener action){
        addNewEmployeeButton.addActionListener(action);
    }

    public void setManageEventsAction(ActionListener action){
        manageEventsButton.addActionListener(action);
    }

    public JPanel getPanel() {
        return contentPane;
    }
}
