import data_structures.Employee;
import data_structures.Event;
import data_structures.PinNumber;
import managers.EmployeeManager;
import managers.EventManager;
import managers.TimeClockManager;
import swing_frames.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

public class UserInterfaceController {
    private EmployeeManager employeeManager;
    private EventManager eventManager;

    private void show(JPanel contentPanel, int closeBehavior){
        JFrame frame = new JFrame();
        frame.setContentPane(contentPanel);
        frame.setDefaultCloseOperation(closeBehavior);
        frame.pack();
        frame.setVisible(true);
    }

    public void setEmployeeManager(EmployeeManager manager){
        this.employeeManager = manager;
    }

    public void setEventManager(EventManager manager) {
        this.eventManager = manager;
    }

    public void mainEmployeeMenu() {
        employeeManager.fetchEmployees();
        eventManager.fetchEvents();
        EmployeeMenuFrame menu = new EmployeeMenuFrame();
        menu.setAddNewEmployeeAction(e -> addEmployee());
        menu.setAttendEventAction(e -> attendEvent());
        menu.setManageEventsAction(e -> manageEvents());
        menu.setTimeClockAction(e -> timeClock());
        menu.setViewEmployeesAction(e -> viewEmployees());
        show(menu.getPanel(), JFrame.EXIT_ON_CLOSE);
    }

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
                boolean clockedIn = clock.checkIfClockedIn(matchingEmployee);
                if (clockedIn){
                    clock.clockOut(matchingEmployee);
                    JOptionPane.showMessageDialog(null, pin + " Clocked out.");
                } else {
                    clock.clockIn(matchingEmployee);
                    JOptionPane.showMessageDialog(null, pin + " Clocked in.");
                }
                employeeManager.updateEmployee(matchingEmployee);
                employeeManager.storeEmployees();
            } else {
                JOptionPane.showMessageDialog(null, "Unknown Pin");
            }
            clockFrame.clearPin();
        }
    }

    public void addEmployee() {
        if (getConfirmation()) {
            employeeManager.fetchEmployees();
            AddEmployeeFrame addEmployeeFrame = new AddEmployeeFrame();

            addEmployeeFrame.setConfirmAction(e -> onAddNewEmployee(addEmployeeFrame));
            addEmployeeFrame.setCloseAction(e -> ((JFrame) addEmployeeFrame.getPanel().getRootPane().getParent()).dispose());

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

    public void attendEvent() {
        AttendEventFrame attendEventFrame = new AttendEventFrame();
        buildEventBox(attendEventFrame);
        attendEventFrame.setConfirmAction(e -> onConfirmAttend(attendEventFrame));
        show(attendEventFrame.getPanel(), JFrame.DISPOSE_ON_CLOSE);
    }

    private void onConfirmAttend(AttendEventFrame attendEventFrame) {
        Event selected = eventManager.getEvent(attendEventFrame.getSelectedEvent());
        if (!attendEventFrame.getPin().isBlank()) {
            PinNumber pin = new PinNumber(attendEventFrame.getPin());
            Employee employee = employeeManager.getEmployee(pin);
            if (employee != null && getConfirmation()) {
                int pointTotal = employee.getPoints() + selected.getPointWorth();
                employee.setPoints(pointTotal);
                employeeManager.updateEmployee(employee);
            }
            attendEventFrame.clearPin();
        }
    }

    private void buildEventBox(AttendEventFrame attendEventFrame) {
        for (Event event : eventManager.getEventMap().values()) {
            attendEventFrame.addEventBoxItem(event.getEventCode());
        }

    }

    public void viewEmployees() {
        ManageEmployeesFrame manageEmployeesFrame = new ManageEmployeesFrame();
        if (getConfirmation()) {
            manageEmployeesFrame.setTableModel(makeEmployeeModel());
            manageEmployeesFrame.setNewRowAction(e -> newEmployeeTableRow(manageEmployeesFrame));
            manageEmployeesFrame.setRemoveRowAction(e -> removeRowsFrom(manageEmployeesFrame.getTable()));
            manageEmployeesFrame.setOKAction(e -> saveEmployeesTable(manageEmployeesFrame));
            manageEmployeesFrame.setCancelAction(e -> closePanel(manageEmployeesFrame.getPanel()));
            show(manageEmployeesFrame.getPanel(), JFrame.DISPOSE_ON_CLOSE);

        }
    }

    private void removeRowsFrom(JTable table) {
        if (JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION) == 0) {
            int[] rows = table.getSelectedRows();
            Arrays.sort(rows); // So the index shrinks from last first.
            for (int i = rows.length - 1; i >= 0; i--) {
                ((DefaultTableModel) table.getModel()).removeRow(i);
            }
        }
    }

    private void newEmployeeTableRow(ManageEmployeesFrame manageEmployeesFrame) {
        DefaultTableModel table = (DefaultTableModel) manageEmployeesFrame.getModel();
        String now = LocalDateTime.now().toString();
        table.addRow(new String[]{"", "", "0:0", "0:0", "0", now, now});
    }

    private TableModel makeEmployeeModel() {
        DefaultTableModel employeeTableModel = new DefaultTableModel();
        employeeTableModel.addColumn("Pin");
        employeeTableModel.addColumn("Name");
        employeeTableModel.addColumn("Weekly Hours");
        employeeTableModel.addColumn("Total Hours");
        employeeTableModel.addColumn("Points");
        employeeTableModel.addColumn("Last Clock In");
        employeeTableModel.addColumn("Last Clock Out");
        HashMap<PinNumber, Employee> employeeMap = (HashMap<PinNumber, Employee>) employeeManager.getEmployeeMap();
        for (Employee employee : employeeMap.values()) {
            Vector<String> newRow = new Vector<>();
            newRow.add(employee.getPin().toString());
            newRow.add(employee.getName());
            newRow.add(employee.getWeeklyHours().toHoursPart() + ":" + employee.getWeeklyHours().toMinutesPart());
            newRow.add(employee.getTotalHours().toHoursPart() + ":" + employee.getTotalHours().toMinutesPart());
            newRow.add(String.valueOf(employee.getPoints()));
            newRow.add(employee.getLastClockInTime().toString());
            newRow.add(employee.getLastClockOutTime().toString());
            employeeTableModel.addRow(newRow);
        }

        return employeeTableModel;
    }

    private void closePanel(JPanel panel) {
        ((JFrame) panel.getRootPane().getParent()).dispose();
    }

    private void saveEmployeesTable(ManageEmployeesFrame manageEmployeesFrame) {
        DefaultTableModel tableModel = (DefaultTableModel) manageEmployeesFrame.getModel();
        Vector<Vector> rows = tableModel.getDataVector();
        HashMap<PinNumber, Employee> newMap = new HashMap<>();
        for (Vector<String> row : rows) {
            Employee newEmployee = new Employee();
            PinNumber pin = new PinNumber(row.get(0));
            newEmployee.setPin(pin);
            String name = row.get(1);
            newEmployee.setName(name);
            String hoursPart = row.get(2).split(":")[0];
            String minutesPart = row.get(2).split(":")[1];
            Duration weeklyHours = Duration.ofHours(Integer.parseInt(hoursPart)).plusMinutes(Integer.parseInt(minutesPart));
            newEmployee.setWeeklyHours(weeklyHours);
            hoursPart = row.get(3).split(":")[0];
            minutesPart = row.get(3).split(":")[1];
            Duration totalHours = Duration.ofHours(Integer.parseInt(hoursPart)).plusMinutes(Integer.parseInt(minutesPart));
            newEmployee.setTotalHours(totalHours);
            int points = Integer.parseInt(row.get(4));
            newEmployee.setPoints(points);
            newEmployee.setLastClockInTime(LocalDateTime.parse(row.get(5)));
            newEmployee.setLastClockOutTime(LocalDateTime.parse(row.get(6)));
            newMap.put(pin, newEmployee);
        }
        employeeManager.setEmployeeMap(newMap);
        employeeManager.storeEmployees();
        closePanel(manageEmployeesFrame.getPanel());
    }

    public void manageEvents() {
        ManageEventsFrame manageEventsFrame = new ManageEventsFrame();

        if (getConfirmation()) {
            manageEventsFrame.setTableModel(makeEventModel(manageEventsFrame));
            manageEventsFrame.setNewRowAction(e -> newEventTableRow(manageEventsFrame));
            manageEventsFrame.setRemoveRowAction(e -> removeRowsFrom(manageEventsFrame.getTable()));
            manageEventsFrame.setCancelAction(e -> closePanel(manageEventsFrame.getPanel()));
            manageEventsFrame.setConfirmAction(e -> saveEventsTable(manageEventsFrame));
            show(manageEventsFrame.getPanel(), JFrame.DISPOSE_ON_CLOSE);
        }
    }

    private void saveEventsTable(ManageEventsFrame manageEventsFrame) {
        DefaultTableModel tableModel = (DefaultTableModel) manageEventsFrame.getModel();
        Vector<Vector> rows = tableModel.getDataVector();
        HashMap<String, Event> newEventMap = new HashMap<>();
        for (Vector<String> row : rows) {
            String code = row.get(0);
            String name = row.get(1);
            int worth = Integer.parseInt(row.get(2));
            String desc = row.get(3);
            Event event = new Event(code, worth);
            event.setEventName(name);
            event.setEventDescription(desc);
            newEventMap.put(code, event);
        }
        eventManager.setEventMap(newEventMap);
        eventManager.storeEvents();
        closePanel(manageEventsFrame.getPanel());
    }

    private void newEventTableRow(ManageEventsFrame frame) {
        DefaultTableModel table = (DefaultTableModel) frame.getModel();
        table.addRow(new String[]{"NewCode", "", "0", ""});
    }

    private TableModel makeEventModel(ManageEventsFrame manageEventsFrame) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Code");
        model.addColumn("Name");
        model.addColumn("Worth");
        model.addColumn("Description");
        HashMap<String, Event> eventMap = (HashMap<String, Event>) eventManager.getEventMap();
        for (Event event : eventMap.values()) {
            String code = event.getEventCode();
            int worth = event.getPointWorth();
            String name = event.getEventName();
            String desc = event.getEventDescription();
            Vector<String> newRow = new Vector<>();
            newRow.add(code);
            newRow.add(name);
            newRow.add(String.valueOf(worth));
            newRow.add(desc);
            model.addRow(newRow);
        }
        return model;
    }

    public boolean getConfirmation() {
        JPasswordField passwordField = new JPasswordField();
        JOptionPane.showConfirmDialog(null, passwordField, "Enter password to confirm:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        return String.valueOf(passwordField.getPassword()).hashCode() == 552231974;
    }
}
