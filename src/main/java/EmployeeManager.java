import java.util.*;

public class EmployeeManager {
    private EmployeeIO employeeIO;
    private Map<PinNumber,Employee> employeeMap;

    public EmployeeManager(){
        employeeMap = new HashMap<PinNumber, Employee>();
    }

    public void loadEmployees(){
        while (employeeIO.hasNextEmployee()) {
            Employee nextEmployee = employeeIO.getNextEmployee();
            employeeMap.put(nextEmployee.getPin(), nextEmployee);
        }
    }

    public Map<PinNumber, Employee> getEmployeeMap(){
        return employeeMap;
    }

    public void newEmployee(Employee employee){
        employeeIO.addNew(employee);
        employeeMap.put(employee.getPin(), employee);
    }

    public void removeEmployee(Employee employee){
        employeeIO.remove(employee);
        employeeMap.remove(employee.getPin());
    }

    public void updateEmployee(Employee employee){
        employeeIO.update(employee);
        employeeMap.put(employee.getPin(), employee);
    }

    public void changeEmployeePin(Employee employee, PinNumber pin){
        removeEmployee(employee);
        employee.setPin(pin);
        newEmployee(employee);
    }

}
