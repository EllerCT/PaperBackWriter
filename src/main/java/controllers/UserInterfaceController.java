package controllers;

import managers.EmployeeManager;
import managers.EventManager;
import managers.ModularProductManager;
import managers.ResourceManager;
import swing_frames.MainMenuFrame;

import javax.swing.*;

public class UserInterfaceController {

    private JFrame showNewWindow(JFrame frame, int closeBehavior) {
        // This is ridiculous but necessary
        frame.setContentPane(frame.getContentPane());

        frame.setDefaultCloseOperation(closeBehavior);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }

    public void Launch(
            EventManager eventManager,
            EmployeeManager employeeManager,
            ModularProductManager modularProductManager,
            ResourceManager resourceManager) {

        EmployeeMenuController employeeMenuController = new EmployeeMenuController(employeeManager, eventManager);
        ProductMenuController productMenuController = new ProductMenuController(modularProductManager, resourceManager);

        MainMenuFrame mainMenu = new MainMenuFrame();
        mainMenu.setEmployeeButtonAction(e -> employeeMenuController.mainEmployeeMenu());
        mainMenu.setProductButtonAction(e -> productMenuController.mainProductsMenu());
        showNewWindow(mainMenu, JFrame.EXIT_ON_CLOSE)
                .setTitle("Paper Back Writer");
    }

}
