package controllers;

import swing_frames.DesktopFrame;

import javax.swing.*;

public class DesktopController {
    private DesktopFrame desktopFrame;
    private EmployeeController employeeController;
    private ProductController productController;

    public DesktopController() {
        this.desktopFrame = new DesktopFrame();
    }

    public void setSubControllers(EmployeeController employeeController, ProductController productController) {
        this.employeeController = employeeController;
        this.productController = productController;
    }


    public void open() {
        JFrame frame = new JFrame("Paper Back Writer");
        frame.setContentPane(desktopFrame.getContentPane());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        configureButtons();
    }

    private void configureButtons() {
        desktopFrame.setAttendanceButtonAction(e -> show(employeeController.attendEvent(), "PBW - Event Attendance"));
        desktopFrame.setClockInOutButtonAction(e -> show(employeeController.timeClock(), "PBW - Time Clock"));
        desktopFrame.setCostAnalysisButtonAction(e -> show(productController.costAnalysis(), "PBW - Cost Analysis"));
        desktopFrame.setEmployeesButtonAction(e -> show(employeeController.viewEmployees(), "PBW - Employee Browser"));
        desktopFrame.setEventsButtonAction(e -> show(employeeController.manageEvents(), "PBW - Event Browser"));
        desktopFrame.setProductBrowserButtonAction(e -> show(productController.productBrowser(this), "PBW - Product Browser"));
        desktopFrame.setResourcesButtonAction(e -> show(productController.resources(), "PBW - Resource Browser"));
    }

    public void show(JInternalFrame window, String title) {
        if (window == null) return;
        JInternalFrame internalFrame = window;
        window.setMaximizable(true);
        window.setResizable(true);
        window.setClosable(true);
        if (title == null) title = "";
        window.setTitle(title);
        internalFrame.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        internalFrame.setContentPane(window.getContentPane());
        desktopFrame.showInnerWindow(internalFrame);
        internalFrame.pack();
        internalFrame.setVisible(true);
    }
}
