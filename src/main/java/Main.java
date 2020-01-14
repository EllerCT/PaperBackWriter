import io_pipes.EmployeeIOPipe;
import io_pipes.EventIOPipe;
import io_pipes.ProductIOPipe;
import io_pipes.ResourceIOPipe;
import io_systems.IOSystem;
import io_systems.LocalFileIOSystem;
import managers.EmployeeManager;
import managers.EventManager;
import managers.ProductManager;
import managers.ResourceManager;

import java.io.File;

public class Main {
    public static void main(String[] args){
        EmployeeManager employeeManager = new EmployeeManager();
        EventManager eventManager = new EventManager();
        ResourceManager resourceManager = new ResourceManager();
        ProductManager productManager = new ProductManager();

        IOSystem employeeIOS = new LocalFileIOSystem();
        IOSystem eventIOS = new LocalFileIOSystem();
        IOSystem resourceIOS = new LocalFileIOSystem();
        IOSystem productIOS = new LocalFileIOSystem();

        File employeeFile = new File("EmployeeCSV.csv");
        File eventsFile = new File("EventCSV.csv");
        File resourcesFile = new File("Resources.csv");
        File productsFile = new File("Products.csv");

        employeeIOS.setLocation(employeeFile.toURI());
        eventIOS.setLocation(eventsFile.toURI());
        resourceIOS.setLocation(resourcesFile.toURI());
        productIOS.setLocation(productsFile.toURI());


        EmployeeIOPipe employeePipe = new EmployeeIOPipe(employeeIOS);
        EventIOPipe eventPipe = new EventIOPipe(eventIOS);
        ResourceIOPipe resourcePipe = new ResourceIOPipe(resourceIOS);
        ProductIOPipe productPipe = new ProductIOPipe(productIOS);

        employeeManager.setEmployeeIOPipe(employeePipe);
        eventManager.setEventIOPipe(eventPipe);
        resourceManager.setResourceIOPipe(resourcePipe);
        productManager.setProductIOPipe(productPipe);

        UserInterfaceController uic = new UserInterfaceController();
        uic.setEmployeeManager(employeeManager);
        uic.setEventManager(eventManager);
        uic.setResourceManager(resourceManager);
        uic.setProductManager(productManager);
        uic.mainMenu();
    }
}
