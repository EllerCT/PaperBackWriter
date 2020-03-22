package ui_components.table_models;

import data_structures.Employee;

import java.util.Map;
import java.util.Vector;

public class EmployeeTableModel extends ClassAwareTableModel {
    public EmployeeTableModel(Map employeeMap) {
        addColumn("Pin");
        addColumn("Name");
        addColumn("Weekly Hours");
        addColumn("Total Hours");
        addColumn("Points");
        addColumn("Last Clock In");
        addColumn("Last Clock Out");

        for (Object value : employeeMap.values()) {
            Employee employee = (Employee) value;
            Vector<Object> newRow = new Vector<>();
            newRow.add(employee.getPin());
            newRow.add(employee.getName());
            newRow.add(employee.getWeeklyHours().toHoursPart() + ":" + employee.getWeeklyHours().toMinutesPart());
            newRow.add(employee.getTotalHours().toHoursPart() + ":" + employee.getTotalHours().toMinutesPart());
            newRow.add(employee.getPoints());
            newRow.add(employee.getLastClockInTime().toString());
            newRow.add(employee.getLastClockOutTime().toString());
            addRow(newRow);
        }
    }
}
