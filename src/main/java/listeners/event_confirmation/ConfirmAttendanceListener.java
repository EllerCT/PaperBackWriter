package listeners.event_confirmation;

import data_structures.Employee;
import data_structures.Event;
import data_structures.PinNumber;
import managers.EmployeeManager;
import managers.EventManager;
import swing_frames.AttendEventFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmAttendanceListener implements ActionListener {

    private AttendEventFrame attendEventFrame;
    private EmployeeManager employeeManager;
    private EventManager eventManager;

    public ConfirmAttendanceListener(AttendEventFrame attendEventFrame, EmployeeManager employeeManager, EventManager eventManager) {
        this.attendEventFrame = attendEventFrame;
        this.employeeManager = employeeManager;
        this.eventManager = eventManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Event selected = eventManager.getEvent(attendEventFrame.getSelectedEvent());
        if (!attendEventFrame.getPin().isBlank()) {
            PinNumber pin = new PinNumber(attendEventFrame.getPin());
            Employee employee = employeeManager.getEmployee(pin);
            if (employee != null) {
                if (selected.getEventConfirmationCode().isBlank()) {
                    System.out.println(selected.getEventConfirmationCode());
                    creditEvent(selected, employee);
                } else {
                    String code = JOptionPane.showInputDialog("Please enter the confirmation code for this event.");
                    if (code == null) code = "";
                    if (code.equals(selected.getEventConfirmationCode()))
                        creditEvent(selected, employee);
                    else JOptionPane.showMessageDialog(null, "That code was incorrect.");
                }
            }
            attendEventFrame.clearPin();
        }
    }

    private void creditEvent(Event selected, Employee employee) {
        int pointTotal = employee.getPoints() + selected.getPointWorth();
        employee.setPoints(pointTotal);
        employeeManager.updateEmployee(employee);
        employeeManager.storeEmployees();
        JOptionPane.showMessageDialog(null, "Confirmed attendance of " + employee.getName() + " to event: " + selected.getEventName());
    }
}
