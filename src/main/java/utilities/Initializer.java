package utilities;

import controllers.DesktopController;
import controllers.EmployeeController;
import controllers.ProductController;
import data_structures.ModularProduct;
import io_pipes.EmployeeIOPipe;
import io_pipes.EventIOPipe;
import io_pipes.ModularProductIOPipe;
import io_pipes.ResourceIOPipe;
import io_systems.IOSystem;
import io_systems.LocalFileIOSystem;
import managers.EmployeeManager;
import managers.EventManager;
import managers.ModularProductManager;
import managers.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Map;

//TODO: Make this a bit more readable?
public class Initializer implements Runnable {

    // These don't need to be convertable, but are here for convenience
    private final String RESOURCE_FILEPATH = "Resources.csv";
    private final String EVENT_FILEPATH = "EventCSV.csv";
    private final String PRODUCT_FILEPATH = "Products.csv";
    private final String EMPLOYEE_FILEPATH = "EmployeeCSV.csv";

    public void run() {
        Settings.getInstance().load();
        ModularProduct.setCurrentID(Settings.getInstance().read("currentID", "0"));

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        setFontSize(18);

        EmployeeManager employeeManager = new EmployeeManager();
        EventManager eventManager = new EventManager();
        ResourceManager resourceManager = new ResourceManager();
        ModularProductManager productManager = new ModularProductManager();

        IOSystem employeeIOS = new LocalFileIOSystem();
        IOSystem eventIOS = new LocalFileIOSystem();
        IOSystem resourceIOS = new LocalFileIOSystem();
        IOSystem productIOS = new LocalFileIOSystem();

        configureIOSystems(employeeIOS, eventIOS, resourceIOS, productIOS);

        EmployeeIOPipe employeePipe = new EmployeeIOPipe(employeeIOS);
        EventIOPipe eventPipe = new EventIOPipe(eventIOS);
        ResourceIOPipe resourcePipe = new ResourceIOPipe(resourceIOS);
        ModularProductIOPipe modularProductIOPipe = new ModularProductIOPipe(productIOS);

        employeeManager.setEmployeeIOPipe(employeePipe);
        eventManager.setEventIOPipe(eventPipe);
        resourceManager.setResourceIOPipe(resourcePipe);
        productManager.setIOPipe(modularProductIOPipe);

        DesktopController desktopController = new DesktopController();
        ProductController productController = new ProductController(productManager, resourceManager);
        EmployeeController employeeController = new EmployeeController(employeeManager, eventManager);
        desktopController.setSubControllers(employeeController, productController);
        desktopController.open();
    }

    private void configureIOSystems(IOSystem employeeIOS, IOSystem eventIOS, IOSystem resourceIOS, IOSystem productIOS) {
        File employeeFile = new File(EMPLOYEE_FILEPATH);
        File eventsFile = new File(EVENT_FILEPATH);
        File resourcesFile = new File(RESOURCE_FILEPATH);
        File productsFile = new File(PRODUCT_FILEPATH);

        employeeIOS.setLocation(employeeFile.toURI());
        eventIOS.setLocation(eventsFile.toURI());
        resourceIOS.setLocation(resourcesFile.toURI());
        productIOS.setLocation(productsFile.toURI());
    }

    private void setFontSize(Integer fontSize) {
        UIDefaults defaults = UIManager.getLookAndFeelDefaults();
        for (Map.Entry<Object, Object> entry : defaults.entrySet()) {
            if (entry.getKey().toString().contains(".font")) {
                defaults.put(entry.getKey(), new Font("Dialog", Font.PLAIN, fontSize));
            }
        }
    }
}
