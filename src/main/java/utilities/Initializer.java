package utilities;

import controllers.UserInterfaceController;
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
public class Initializer {
    public void run() {
        Settings.load();
        ///////////////////////////////////// TEMPORARY
        new Updater().updateProductFile(); // TODO: Remove
        ///////////////////////////////////// TEMPORARY
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        setFontSize(18);
        ModularProduct.setCurrentID(Settings.read("currentID", "0"));
        EmployeeManager employeeManager = new EmployeeManager();
        EventManager eventManager = new EventManager();
        ResourceManager resourceManager = new ResourceManager();
        ModularProductManager productManager = new ModularProductManager();

        IOSystem employeeIOS = new LocalFileIOSystem();
        IOSystem eventIOS = new LocalFileIOSystem();
        IOSystem resourceIOS = new LocalFileIOSystem();
        IOSystem productIOS = new LocalFileIOSystem();

        //TODO: Make these configurable?
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
        ModularProductIOPipe modularProductIOPipe = new ModularProductIOPipe(productIOS);

        employeeManager.setEmployeeIOPipe(employeePipe);
        eventManager.setEventIOPipe(eventPipe);
        resourceManager.setResourceIOPipe(resourcePipe);
        productManager.setIOPipe(modularProductIOPipe);

        UserInterfaceController uic = new UserInterfaceController();

        uic.Launch(eventManager, employeeManager, productManager, resourceManager);
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
