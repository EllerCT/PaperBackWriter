import java.util.*;

public class EmployeeManager {
    private EmployeeIOPipe employeeIOPipe;
    private Map<PinNumber,Employee> employeeMap;

    public EmployeeManager(){
        employeeMap = new HashMap<PinNumber, Employee>();
    }

    public void fetchEmployees(){
        employeeMap.clear();
        employeeMap.putAll(employeeIOPipe.loadEmployees());
    }

    public void storeEmployees(){
        employeeIOPipe.saveEmployees(employeeMap);
    }

    public void setEmployeeIOPipe(EmployeeIOPipe employeeIOPipe){
        this.employeeIOPipe = employeeIOPipe;
    }

    public Map<PinNumber, Employee> getEmployeeMap(){
        return employeeMap;
    }

    public void newEmployee(Employee employee){
        employeeMap.put(employee.getPin(), employee);
    }

    public void removeEmployee(Employee employee){
        employeeMap.remove(employee.getPin());
    }

    /**
     *  Take a given Employee, and changes the stored employee of the same pin
     *  to match the given employee.
     * @param employee The updated employee
     */
    public void updateEmployee(Employee employee){
        if (employeeMap.containsKey(employee.getPin())) {
            employeeMap.put(employee.getPin(), employee);
        }
    }

    /**
     * Take a given employee with the old pin number, and assign them a new
     * pin number.
     * @param employee The employee with the old pin number.
     * @param pin The new pin number.
     */
    public void changeEmployeePin(Employee employee, PinNumber pin){
        removeEmployee(employee);
        employee.setPin(pin);
        newEmployee(employee);
    }

    /**
     * Get a new instance of Employee identical to the Employee stored under the given
     * PinNumber
     * @param pin The PinNumber of the Employee to match
     * @return New instance of the matching Employee
     */
    public Employee getEmployee(PinNumber pin) {
        if (this.employeeMap.containsKey(pin)){
            Employee mapEmployee = this.employeeMap.get(pin);
            Employee safeEmployee = new Employee();
            safeEmployee.setWeeklyHours(mapEmployee.getWeeklyHours());
            safeEmployee.setTotalHours(mapEmployee.getTotalHours());
            safeEmployee.setLastClockInTime(mapEmployee.getLastClockInTime());
            safeEmployee.setLastClockOutTime(mapEmployee.getLastClockOutTime());
            safeEmployee.setPoints(mapEmployee.getPoints());
            safeEmployee.setPin(mapEmployee.getPin());
            safeEmployee.setName(mapEmployee.getName());
            return this.employeeMap.get(pin);
        }
        return null;
    }
}
