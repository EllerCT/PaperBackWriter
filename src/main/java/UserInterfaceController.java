import swing_frames.*;
import javax.swing.*;
import java.util.Map;

public class UserInterfaceController implements TimeclockFrontend{
    private Map<PinNumber, Employee> employeeMap;
    private void show(JPanel contentPanel, int closeBehavior){
        JFrame frame = new JFrame();
        frame.setContentPane(contentPanel);
        frame.setDefaultCloseOperation(closeBehavior);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void mainMenu() {
        MainMenuFrame menu = new MainMenuFrame();
        menu.setAddNewEmployeeAction(e -> addEmployee());
        menu.setAttendEventAction(e -> attendEvent());
        menu.setManageEventsAction(e -> manageEvents());
        menu.setTimeClockAction(e -> timeClock());
        menu.setViewEmployeesAction(e -> viewEmployees());
        show(menu.getPanel(), JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void timeClock() {
        TimeClockFrame clockFrame = new TimeClockFrame();

        //TODO: Tie logic to listener
        //TODO: Demand manager password when clocking in.

        show(clockFrame.getPanel(), JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void addEmployee() {
        AddEmployeeFrame addEmployeeFrame = new AddEmployeeFrame();

        //TODO: Tie logic to listener
        //TODO: Demand manager password to even open this

        show(addEmployeeFrame.getPanel(), JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void attendEvent() {
        AttendEventFrame attendEventFrame = new AttendEventFrame();

        //TODO: Tie logic to listeners
        //TODO: Demand manager password before allowing this to go through.

        show(attendEventFrame.getPanel(), JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void viewEmployees() {
        EmployeesFrame employeesFrame = new EmployeesFrame();

        //TODO: Tie logic to listeners
        //TODO: Demand manager password before permitting this to open

        show(employeesFrame.getPanel(), JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void manageEvents() {
        ManageEventsFrame manageEventsFrame = new ManageEventsFrame();

        //TODO: Tie logic to listeners
        //TODO: Demand manager password before permitting this to open

        show(manageEventsFrame.getPanel(), JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void getConfirmation() {

    }
}
