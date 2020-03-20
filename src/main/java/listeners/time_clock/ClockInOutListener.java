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
            Employee matchingEmployee = (Employee) employeeManager.getFromKey(pin);
            if (matchingEmployee != null) {
                if (checkIfShouldContinueWith(matchingEmployee)) {
                    boolean clockedIn = clock.checkIfClockedIn(matchingEmployee);
                    if (clockedIn) {
                        clock.clockOut(matchingEmployee);
                        JOptionPane.showMessageDialog(null, pin + ": " + matchingEmployee.getName() + " clocked out.");
                    } else {
                        clock.clockIn(matchingEmployee);
                        JOptionPane.showMessageDialog(null, pin + ": " + matchingEmployee.getName() + " clocked in.");
                    }
                    employeeManager.update(matchingEmployee);
                    employeeManager.store();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Unknown Pin");
            }
            clockFrame.clearPin();
        }
    }

    private boolean checkIfShouldContinueWith(Employee employee) {
        int result = JOptionPane.showConfirmDialog(null, String.format("Please confirm:%nName:%s. Pin: %s.\nIs this you?", employee.getName(), employee.getPin()), "Confirmation", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }
}
