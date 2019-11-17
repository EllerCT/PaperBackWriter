import io_pipes.EmployeeIOPipe;
import io_pipes.EventIOPipe;
import io_systems.IOSystem;
import io_systems.LocalFileIOSystem;
import managers.EmployeeManager;
import managers.EventManager;

import java.io.File;

public class Main {
    public static void main(String[] args){
        EmployeeManager employeeManager = new EmployeeManager();
        EventManager eventManager = new EventManager();

        IOSystem employeeIOS = new LocalFileIOSystem();
        IOSystem eventIOS = new LocalFileIOSystem();

        File employeeFile = new File("EmployeeCSV.csv");
        File eventsFile = new File("EventCSV.csv");

        employeeIOS.setLocation(employeeFile.toURI());
        eventIOS.setLocation(eventsFile.toURI());

        EmployeeIOPipe employeePipe = new EmployeeIOPipe(employeeIOS);
        EventIOPipe eventPipe = new EventIOPipe(eventIOS);

        employeeManager.setEmployeeIOPipe(employeePipe);
        eventManager.setEventIOPipe(eventPipe);

        UserInterfaceController uic = new UserInterfaceController();
        uic.setEmployeeManager(employeeManager);
        uic.setEventManager(eventManager);
        uic.mainMenu();
    }
}
