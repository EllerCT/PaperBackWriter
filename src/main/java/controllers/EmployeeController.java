package controllers;

import data_structures.Event;
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
import ui_components.table_models.EmployeeTableModel;
import ui_components.table_models.EventTableModel;
import utilities.Security;

import javax.swing.*;

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
            manageEmployeesFrame.setTableModel(new EmployeeTableModel(employeeManager.getMap()));
            manageEmployeesFrame.setNewRowAction(new AddEmployeeRowListener(table));
            manageEmployeesFrame.setRemoveRowAction(new RemoveRowListener(table));
            manageEmployeesFrame.setOKAction(new SaveEmployeeTableListener(manageEmployeesFrame, employeeManager));
            manageEmployeesFrame.setCancelAction(e -> manageEmployeesFrame.dispose());
            return manageEmployeesFrame;
        }
        return null;
    }

    public ManageEventsFrame manageEvents() {
        ManageEventsFrame manageEventsFrame = new ManageEventsFrame();
        JTable table = manageEventsFrame.getTable();
        if (new Security().getAuthorization()) {
            manageEventsFrame.setTableModel(new EventTableModel(eventManager.getMap()));
            manageEventsFrame.setNewRowAction(new AddEventRowListener(table));
            manageEventsFrame.setRemoveRowAction(new RemoveRowListener(table));
            manageEventsFrame.setCancelAction(e -> manageEventsFrame.dispose());
            manageEventsFrame.setConfirmAction(new SaveEventTableListener(manageEventsFrame, eventManager));
            return manageEventsFrame;
        }
        return null;
    }
}
