package managers;

import data_structures.Employee;
import data_structures.PinNumber;

public class EmployeeManager extends AbstractManager {
    public void add(Object object) {
        if (object instanceof Employee) {
            PinNumber key = Employee.generateKeyFor((Employee) object);
            super.map.put(key, object);
        }
    }

    public void remove(Object object) {
        if (object instanceof Employee) {
            Employee employee = (Employee) object;
            PinNumber key = Employee.generateKeyFor(employee);
            super.map.remove(key);
        }
    }
}
