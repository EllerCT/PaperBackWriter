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
import swing_frames.AttendEventFrame;
import swing_frames.ManageEmployeesFrame;
import swing_frames.ManageEventsFrame;
import swing_frames.TimeClockFrame;
import utilities.Security;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.HashMap;
import java.util.Vector;

public class EmployeeController {

    private EmployeeManager employeeManager;
    private EventManager eventManager;

    public EmployeeController(EmployeeManager employeeManager, EventManager eventManager) {
        this.employeeManager = employeeManager;
        employeeManager.fetch();
        this.eventManager = eventManager;
        eventManager.fetch();
    }

    public TimeClockFrame timeClock() {
        TimeClockFrame clockFrame = new TimeClockFrame();
        clockFrame.setClockInOutAction(new ClockInOutListener(clockFrame, employeeManager));
        return clockFrame;
    }

    public AttendEventFrame attendEvent() {
        AttendEventFrame attendEventFrame = new AttendEventFrame();
        buildEventBox(attendEventFrame);
        attendEventFrame.setConfirmAction(new ConfirmAttendanceListener(attendEventFrame, employeeManager, eventManager));
        return attendEventFrame;
    }

    private void buildEventBox(AttendEventFrame attendEventFrame) {
        for (Object entry : eventManager.getMap().values()) {
            Event event = (Event) entry;
            attendEventFrame.addEventBoxItem(event.getEventCode());
        }

    }

    public ManageEmployeesFrame viewEmployees() {
        ManageEmployeesFrame manageEmployeesFrame = new ManageEmployeesFrame();
        JTable table = manageEmployeesFrame.getTable();
        if (new Security().getAuthorization()) {
            manageEmployeesFrame.setTableModel(makeEmployeeModel());
            manageEmployeesFrame.setNewRowAction(new AddEmployeeRowListener(table));
            manageEmployeesFrame.setRemoveRowAction(new RemoveRowListener(table));
            manageEmployeesFrame.setOKAction(new SaveEmployeeTableListener(manageEmployeesFrame, employeeManager));
            manageEmployeesFrame.setCancelAction(e -> manageEmployeesFrame.dispose());
            return manageEmployeesFrame;
        }
        return null;
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
        HashMap<PinNumber, Employee> employeeMap = new HashMap(employeeManager.getMap());
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

    public ManageEventsFrame manageEvents() {
        ManageEventsFrame manageEventsFrame = new ManageEventsFrame();
        JTable table = manageEventsFrame.getTable();
        if (new Security().getAuthorization()) {
            manageEventsFrame.setTableModel(makeEventModel());
            manageEventsFrame.setNewRowAction(new AddEventRowListener(table));
            manageEventsFrame.setRemoveRowAction(new RemoveRowListener(table));
            manageEventsFrame.setCancelAction(e -> manageEventsFrame.dispose());
            manageEventsFrame.setConfirmAction(new SaveEventTableListener(manageEventsFrame, eventManager));
            return manageEventsFrame;
        }
        return null;
    }

    private TableModel makeEventModel() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Code");
        model.addColumn("Name");
        model.addColumn("Worth");
        model.addColumn("Description");
        model.addColumn("Confirmation Code");
        HashMap<String, Event> eventMap = new HashMap(eventManager.getMap());
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
