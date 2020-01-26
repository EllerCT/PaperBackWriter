package listeners.time_clock;

import data_structures.Employee;
import data_structures.PinNumber;
import managers.EmployeeManager;
import swing_frames.TimeClockFrame;
import utilities.TimeClock;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClockInOutListener implements ActionListener {

    private TimeClockFrame clockFrame;
    private EmployeeManager employeeManager;

    public ClockInOutListener(TimeClockFrame clockFrame, EmployeeManager employeeManager) {
        this.clockFrame = clockFrame;
        this.employeeManager = employeeManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TimeClock clock = new TimeClock();
        if (!clockFrame.getPin().isBlank()) {
            PinNumber pin = new PinNumber(clockFrame.getPin());
            Employee matchingEmployee = employeeManager.getEmployee(pin);
            if (matchingEmployee != null) {
                boolean clockedIn = clock.checkIfClockedIn(matchingEmployee);
                if (clockedIn) {
                    clock.clockOut(matchingEmployee);
                    JOptionPane.showMessageDialog(null, pin + ": " + matchingEmployee.getName() + " clocked out.");
                } else {
                    clock.clockIn(matchingEmployee);
                    JOptionPane.showMessageDialog(null, pin + ": " + matchingEmployee.getName() + " clocked in.");
                }
                employeeManager.updateEmployee(matchingEmployee);
                employeeManager.storeEmployees();
            } else {
                JOptionPane.showMessageDialog(null, "Unknown Pin");
            }
            clockFrame.clearPin();
        }
    }
}
