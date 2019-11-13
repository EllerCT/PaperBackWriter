import data_structures.Employee;
import data_structures.PinNumber;
import managers.EmployeeManager;
import managers.TimeClockManager;
import swing_frames.*;

import javax.swing.*;
import java.time.Duration;
import java.time.LocalDateTime;

public class UserInterfaceController implements TimeclockFrontend{
    private EmployeeManager employeeManager;

    private void show(JPanel contentPanel, int closeBehavior){
        JFrame frame = new JFrame();
        frame.setContentPane(contentPanel);
        frame.setDefaultCloseOperation(closeBehavior);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    public void setEmployeeManager(EmployeeManager manager){
        this.employeeManager = manager;
    }

    @Override
    public void mainMenu() {
        employeeManager.fetchEmployees();
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
        clockFrame.setClockInOutAction(e -> clockInOut(clockFrame));
        show(clockFrame.getPanel(), JFrame.DISPOSE_ON_CLOSE);
    }

    private void clockInOut(TimeClockFrame clockFrame) {
        TimeClockManager clock = new TimeClockManager();
        if (!clockFrame.getPin().isBlank()){
            PinNumber pin = new PinNumber(clockFrame.getPin());
            Employee matchingEmployee = employeeManager.getEmployee(pin);
            if(matchingEmployee != null){
                boolean clockedIn = matchingEmployee.getLastClockInTime().isAfter(
                        matchingEmployee.getLastClockOutTime());
                if (clockedIn){
                    clock.clockOut(matchingEmployee);
                } else if (getConfirmation()){
                    clock.clockIn(matchingEmployee);
                }
                employeeManager.updateEmployee(matchingEmployee);
            }
        }
    }

    @Override
    public void addEmployee() {
        if (getConfirmation()) {
            employeeManager.fetchEmployees();
            AddEmployeeFrame addEmployeeFrame = new AddEmployeeFrame();

            addEmployeeFrame.setConfirmAction(e -> onAddNewEmployee(addEmployeeFrame));
            addEmployeeFrame.setCloseAction(e -> ((JFrame) addEmployeeFrame.getPanel().getParent()).dispose());

            show(addEmployeeFrame.getPanel(), JFrame.DISPOSE_ON_CLOSE);
        }
    }

    private void onAddNewEmployee(AddEmployeeFrame addEmployeeFrame) {
        Employee employee = new Employee();
        employee.setPin(new PinNumber(addEmployeeFrame.getNewEmployeePin()));
        employee.setName(addEmployeeFrame.getNewEmployeeName());
        employee.setPoints(Integer.parseInt(addEmployeeFrame.getNewEmployeePoints()));
        employee.setLastClockInTime(LocalDateTime.now());
        employee.setLastClockOutTime(LocalDateTime.now());
        long hours = Long.parseLong(addEmployeeFrame.getNewEmployeeHours());
        employee.setWeeklyHours(Duration.ofHours(hours));
        employee.setTotalHours(Duration.ofHours(hours));

        employeeManager.newEmployee(employee);
        employeeManager.storeEmployees();
        addEmployeeFrame.clearFields();
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
    public boolean getConfirmation() {
        return true;
    }
}
