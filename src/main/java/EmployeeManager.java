import java.util.*;

public class EmployeeManager {
    private EmployeeIOPipe employeeIOPipe;
    private Map<PinNumber,Employee> employeeMap;

    public EmployeeManager(){
        employeeMap = new HashMap<PinNumber, Employee>();
    }

    public void getEmployees(){
        //TODO: Implement this - the other version didn't match with needs.
    }

    public void setEmployeeIOPipe(EmployeeIOPipe employeeIOPipe){
        this.employeeIOPipe = employeeIOPipe;
    }

    public Map<PinNumber, Employee> getEmployeeMap(){
        return employeeMap;
    }

    public void newEmployee(Employee employee){
        employeeIOPipe.addNew(employee);
        employeeMap.put(employee.getPin(), employee);
    }

    public void removeEmployee(Employee employee){
        employeeIOPipe.remove(employee);
        employeeMap.remove(employee.getPin());
    }

    public void updateEmployee(Employee employee){
        employeeIOPipe.update(employee);
        employeeMap.put(employee.getPin(), employee);
    }

    public void changeEmployeePin(Employee employee, PinNumber pin){
        removeEmployee(employee);
        employee.setPin(pin);
        newEmployee(employee);
    }

    public Employee getEmployee(PinNumber pin) {
        if (this.employeeMap.containsKey(pin)){
            return this.employeeMap.get(pin);
        }
        return null;
    }
}
