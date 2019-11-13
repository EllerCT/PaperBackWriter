import io_pipes.EmployeeIOPipe;
import io_systems.IOSystem;
import io_systems.LocalFileIOSystem;
import managers.EmployeeManager;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class Main {
    public static void main(String[] args){
        UserInterfaceController uic = new UserInterfaceController();
        EmployeeManager employeeManager = new EmployeeManager();
        IOSystem employeeIOS = new LocalFileIOSystem();
        File hackyNewFile = new File("EmployeeCSV.csv");
        if (!hackyNewFile.exists()) {
            try {
                hackyNewFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        URI fileLocation = hackyNewFile.toURI();
        employeeIOS.setLocation(fileLocation);
        EmployeeIOPipe pipe = new EmployeeIOPipe(employeeIOS);
        employeeManager.setEmployeeIOPipe(pipe);
        uic.setEmployeeManager(employeeManager);
        uic.mainMenu();
    }
}
