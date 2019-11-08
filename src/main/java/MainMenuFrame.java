import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuFrame {
    private JButton clockInOutButton;
    private JButton viewEmployeeInformationButton;
    private JButton addNewEmployeeButton;
    private JButton eventClockButton;
    private JButton manageEventsButton;
    private JPanel mainMenuPanel;

    public MainMenuFrame() {
        clockInOutButton.addActionListener(e -> onClockInOut());
        eventClockButton.addActionListener(e -> onEventClock());
        viewEmployeeInformationButton.addActionListener(e -> onViewEmployee());
        addNewEmployeeButton.addActionListener(e -> onAddEmployee());
        manageEventsButton.addActionListener(e -> onManageEvents());
    }

    private void onManageEvents() {
        UserInterfaceController.show(new ManageEventsFrame().getPanel(), JFrame.DISPOSE_ON_CLOSE);
    }

    private void onViewEmployee() {
        UserInterfaceController.show(new EmployeesFrame().getPanel(), JFrame.DISPOSE_ON_CLOSE);
    }

    private void onAddEmployee() {
        UserInterfaceController.show(new AddEmployeeFrame().getPanel(), JFrame.DISPOSE_ON_CLOSE);
    }

    private void onEventClock() {
        UserInterfaceController.show(new ClockPointsFrame().getPanel(), JFrame.DISPOSE_ON_CLOSE);
    }

    private void onClockInOut() {
        UserInterfaceController.show(new ClockInOutFrame().getPanel(), JFrame.DISPOSE_ON_CLOSE);
    }

    public JPanel getPanel() {
        return mainMenuPanel;
    }
}
