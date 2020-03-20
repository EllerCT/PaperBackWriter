package listeners.employee_browser;

import data_structures.Employee;
import data_structures.PinNumber;
import managers.EmployeeManager;
import swing_frames.ManageEmployeesFrame;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Vector;

public class SaveEmployeeTableListener implements ActionListener {

    private ManageEmployeesFrame manageEmployeesFrame;
    private EmployeeManager employeeManager;

    public SaveEmployeeTableListener(ManageEmployeesFrame manageEmployeesFrame, EmployeeManager employeeManager) {
        this.manageEmployeesFrame = manageEmployeesFrame;
        this.employeeManager = employeeManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
        employeeManager.setMap(newMap);
        employeeManager.store();
        manageEmployeesFrame.dispose();
    }
}
