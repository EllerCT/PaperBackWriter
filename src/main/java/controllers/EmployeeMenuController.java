package controllers;

import data_structures.Employee;
import data_structures.Event;
import data_structures.PinNumber;
import listeners.employee_browser.AddEmployeeRowListener;
import listeners.employee_browser.SaveEmployeeTableListener;
import listeners.event_browser.AddEventRowListener;
import listeners.event_browser.SaveEventTableListener;
import listeners.event_confirmation.ConfirmAttendanceListener;
import listeners.general.RemoveRowListener;
import listeners.time_clock.ClockInOutListener;
import managers.EmployeeManager;
import managers.EventManager;
import swing_frames.*;
import utilities.Security;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Vector;

public class EmployeeMenuController {

    private EmployeeManager employeeManager;
    private EventManager eventManager;

    public EmployeeMenuController(EmployeeManager employeeManager, EventManager eventManager) {
        this.employeeManager = employeeManager;
        this.eventManager = eventManager;
    }

    //TODO: DRY violation
    private JFrame showNewWindow(JFrame frame, int closeBehavior) {
        // This is ridiculous but necessary
        frame.setContentPane(frame.getContentPane());

        frame.setDefaultCloseOperation(closeBehavior);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
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
        showNewWindow(menu, JFrame.DISPOSE_ON_CLOSE)
                .setTitle("PBW - Employee");
    }

    private void timeClock() {
        TimeClockFrame clockFrame = new TimeClockFrame();
        clockFrame.setClockInOutAction(new ClockInOutListener(clockFrame, employeeManager));
        showNewWindow(clockFrame, JFrame.DISPOSE_ON_CLOSE)
                .setTitle("PBW - Clock");
    }


    private void addEmployee() {
        if (new Security().getAuthorization()) {
            employeeManager.fetchEmployees();
            AddEmployeeFrame addEmployeeFrame = new AddEmployeeFrame();

            addEmployeeFrame.setConfirmAction(e -> onAddNewEmployee(addEmployeeFrame));
            addEmployeeFrame.setCloseAction(e -> addEmployeeFrame.dispose());

            showNewWindow(addEmployeeFrame, JFrame.DISPOSE_ON_CLOSE)
                    .setTitle("PBW - Add Employee");
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

    private void attendEvent() {
        AttendEventFrame attendEventFrame = new AttendEventFrame();
        buildEventBox(attendEventFrame);
        attendEventFrame.setConfirmAction(new ConfirmAttendanceListener(attendEventFrame, employeeManager, eventManager));
        showNewWindow(attendEventFrame, JFrame.DISPOSE_ON_CLOSE)
                .setTitle("PBW - Event Attendance");
    }

    private void buildEventBox(AttendEventFrame attendEventFrame) {
        for (Event event : eventManager.getEventMap().values()) {
            attendEventFrame.addEventBoxItem(event.getEventCode());
        }

    }

    private void viewEmployees() {
        ManageEmployeesFrame manageEmployeesFrame = new ManageEmployeesFrame();
        JTable table = manageEmployeesFrame.getTable();
        if (new Security().getAuthorization()) {
            manageEmployeesFrame.setTableModel(makeEmployeeModel());
            manageEmployeesFrame.setNewRowAction(new AddEmployeeRowListener(table));
            manageEmployeesFrame.setRemoveRowAction(new RemoveRowListener(table));
            manageEmployeesFrame.setOKAction(new SaveEmployeeTableListener(manageEmployeesFrame, employeeManager));
            manageEmployeesFrame.setCancelAction(e -> manageEmployeesFrame.dispose());
            showNewWindow(manageEmployeesFrame, JFrame.DISPOSE_ON_CLOSE)
                    .setTitle("PBW - View Employee Infomration");
        }
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

    private void manageEvents() {
        ManageEventsFrame manageEventsFrame = new ManageEventsFrame();
        JTable table = manageEventsFrame.getTable();
        if (new Security().getAuthorization()) {
            manageEventsFrame.setTableModel(makeEventModel());
            manageEventsFrame.setNewRowAction(new AddEventRowListener(table));
            manageEventsFrame.setRemoveRowAction(new RemoveRowListener(table));
            manageEventsFrame.setCancelAction(e -> manageEventsFrame.dispose());
            manageEventsFrame.setConfirmAction(new SaveEventTableListener(manageEventsFrame, eventManager));
            showNewWindow(manageEventsFrame, JFrame.DISPOSE_ON_CLOSE)
                    .setTitle("PBW - Manage Event Information");
        }
    }

    private TableModel makeEventModel() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Code");
        model.addColumn("Name");
        model.addColumn("Worth");
        model.addColumn("Description");
        model.addColumn("Confirmation Code");
        HashMap<String, Event> eventMap = (HashMap<String, Event>) eventManager.getEventMap();
        for (Event event : eventMap.values()) {
            String code = event.getEventCode();
            int worth = event.getPointWorth();
            String name = event.getEventName();
            String desc = event.getEventDescription();
            String confirm = event.getEventConfirmationCode();
            Vector<String> newRow = new Vector<>();
            newRow.add(code);
            newRow.add(name);
            newRow.add(String.valueOf(worth));
            newRow.add(desc);
            newRow.add(confirm);
            model.addRow(newRow);
        }
        return model;
    }
}
