package controllers;

import data_structures.*;
import io_pipes.ResourceIOPipe;
import listeners.cost_analysis.CalculateCostsListener;
import listeners.cost_analysis.SubmitProductListener;
import listeners.employee_browser.AddEmployeeRowListener;
import listeners.employee_browser.SaveEmployeeTableListener;
import listeners.event_browser.AddEventRowListener;
import listeners.event_browser.SaveEventTableListener;
import listeners.event_confirmation.ConfirmAttendanceListener;
import listeners.general.RemoveRowListener;
import listeners.product_viewer.ViewerFinishedListener;
import listeners.resource_browser.AddResourceRowListener;
import listeners.resource_browser.SaveResourceTableListener;
import listeners.time_clock.ClockInOutListener;
import managers.EmployeeManager;
import managers.EventManager;
import managers.ProductManager;
import managers.ResourceManager;
import swing_frames.*;
import utilities.CostAnalyzer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class UserInterfaceController {

    private EmployeeManager employeeManager;
    private EventManager eventManager;
    private ResourceManager resourceManager;
    private ProductManager productManager;


    private JFrame showNewWindow(JFrame frame, int closeBehavior) {
        // This is ridiculous but necessary
        frame.setContentPane(frame.getContentPane());

        frame.setDefaultCloseOperation(closeBehavior);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }

    public void setProductManager(ProductManager manager) {
        this.productManager = manager;
    }

    public void setResourceManager(ResourceManager manager) {
        this.resourceManager = manager;
    }

    public void setEmployeeManager(EmployeeManager manager) {
        this.employeeManager = manager;
    }

    public void setEventManager(EventManager manager) {
        this.eventManager = manager;
    }

    public void mainMenu() {
        MainMenuFrame mainMenu = new MainMenuFrame();
        mainMenu.setEmployeeButtonAction(e -> mainEmployeeMenu());
        mainMenu.setProductButtonAction(e -> mainProductsMenu());
        showNewWindow(mainMenu, JFrame.EXIT_ON_CLOSE)
                .setTitle("Paper Back Writer");
    }

    private void mainProductsMenu() {
        resourceManager.fetchResources();
        ProductsMenuFrame menu = new ProductsMenuFrame();
        menu.setBrowseProductsButtonListener(e -> productBrowser());
        menu.setCostAnalysisButtonListener(e -> costAnalysis());
        menu.setResourcesButtonListener(e -> resources());
        showNewWindow(menu, JFrame.DISPOSE_ON_CLOSE)
                .setTitle("PBW - Products");
    }

    private void resources() {
        ResourcesFrame resources = new ResourcesFrame();
        JTable table = resources.getTable();
        table.setModel(makeResourceModel());
        // Make a combo box and populate it with the types of resources.
        JComboBox<ResourceType> resourceTypeDropdown = new JComboBox<>(ResourceType.values());
        // Use that combo box to edit cells in the 'Type' column.
        table.getColumn("Type").setCellEditor(new DefaultCellEditor(resourceTypeDropdown));
        resources.setSaveButtonListener(new SaveResourceTableListener(resources, resourceManager));
        resources.setAddButtonListener(e -> new AddResourceRowListener(table));
        resources.setRemoveButtonListener(new RemoveRowListener(table));
        resources.setCancelButtonListener(e -> resources.dispose());
        showNewWindow(resources, JFrame.DISPOSE_ON_CLOSE)
                .setTitle("PBW Resources");
    }

    private void productBrowser() {
        productManager.fetchProducts();
        ProductBrowserFrame productBrowser = new ProductBrowserFrame();
        productBrowser.setCloseButtonListener(e -> productBrowser.dispose());
        productBrowser.setViewButtonListener(e -> viewProduct(productBrowser));
        productBrowser.setEnableGradingButtonListener(e -> enableGrading(productBrowser));

        DefaultTableModel model = makeProductModel();
        productBrowser.setProductsTableModel(model);

        showNewWindow(productBrowser, JFrame.DISPOSE_ON_CLOSE)
                .setTitle("PBW Product Browser");
    }

    private void enableGrading(ProductBrowserFrame browser) {
        if (getConfirmation()) {
            browser.enableGradingCheckbox();
        }
    }

    private void viewProduct(ProductBrowserFrame productBrowser) {
        boolean grading = productBrowser.isGrading();
        int selectedRow = productBrowser.getProductsTable().getSelectedRow();
        String id = (String) productBrowser.getProductsTableModel().getValueAt(selectedRow, 0);
        Product selectedProduct = productManager.getProductMap().get(id);

        ProductViewerFrame viewer = new ProductViewerFrame();
        viewer.setOkButtonListener(new ViewerFinishedListener(viewer, productManager, grading, selectedProduct));
        fillViewerFields(viewer, selectedProduct);

        showNewWindow(viewer, JFrame.DISPOSE_ON_CLOSE)
                .setTitle("PBW - Product Viewer");
    }

    private void fillViewerFields(ProductViewerFrame viewer, Product selectedProduct) {
        viewer.setIdNumber(selectedProduct.getId());
        viewer.setName(selectedProduct.getName());
        viewer.setProductType(selectedProduct.getType());
        viewer.setDate(selectedProduct.getDate());
        viewer.setProductDescription(selectedProduct.getDescription());
        viewer.setGrade(selectedProduct.getGrade());
        viewer.setTotalCost(selectedProduct.getTotalCost());
        viewer.setPaperType(selectedProduct.getPaperType());
        viewer.setPaperUnits(selectedProduct.getPaperAmount());
        viewer.setPaperCost(selectedProduct.getPaperCost());
        viewer.setThreadType(selectedProduct.getThreadType());
        viewer.setThreadUnits(selectedProduct.getThreadAmount());
        viewer.setThreadCost(selectedProduct.getThreadCost());
        viewer.setGlueType(selectedProduct.getGlueType());
        viewer.setGlueUnits(selectedProduct.getGlueAmount());
        viewer.setGlueCost(selectedProduct.getGlueCost());
        viewer.setBoardType(selectedProduct.getBoardType());
        viewer.setBoardUnits(selectedProduct.getBoardAmount());
        viewer.setBoardCost(selectedProduct.getBoardCost());
        viewer.setDecoratedPaperType(selectedProduct.getDecoratedPaperType());
        viewer.setDecoratedPaperUnits(selectedProduct.getDecoratedPaperAmount());
        viewer.setDecoratedPaperCost(selectedProduct.getDecoratedPaperCost());
        viewer.setSpineType(selectedProduct.getSpineType());
        viewer.setSpineUnits(selectedProduct.getSpineAmount());
        viewer.setSpineCost(selectedProduct.getSpineCost());
        viewer.setEndBandType(selectedProduct.getEndBandType());
        viewer.setEndBandUnits(selectedProduct.getEndBandAmount());
        viewer.setEndBandCost(selectedProduct.getEndBandCost());
        viewer.setOtherMaterial(selectedProduct.getOther());
        viewer.setOtherUnits(selectedProduct.getOtherAmount());
        viewer.setOtherCost(selectedProduct.getOtherCost());
        viewer.setSpiritsType(selectedProduct.getSpiritType());
        viewer.setSpiritUnits(selectedProduct.getSpiritAmount());
        viewer.setSpiritsCost(selectedProduct.getSpiritCost());
    }

    private DefaultTableModel makeProductModel() {
        // An uneditable table.
        DefaultTableModel model = new DefaultTableModel() {
            public boolean isCellEditable(int x, int y) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Grade");
        model.addColumn("Total Cost");
        for (Product product : productManager.getProductMap().values()) {
            model.addRow(new String[]{product.getId(), product.getName(), product.getGrade(), product.getTotalCost()});
        }
        return model;
    }

    private void costAnalysis() {
        CostAnalysisFrame costAnalysis = new CostAnalysisFrame();
        CostAnalyzer analyser = new CostAnalyzer();
        costAnalysis.setIdNumber(Product.getCurrentID());
        // Populate combo boxes.
        populateCostAnalysisComboBoxes(costAnalysis);

        // Set button behavior
        costAnalysis.setCalculateButtonAction(new CalculateCostsListener(costAnalysis, analyser));
        costAnalysis.setCancelButtonAction(e -> costAnalysis.dispose());
        costAnalysis.setSubmitButtonAction(new SubmitProductListener(costAnalysis, productManager));

        showNewWindow(costAnalysis, JFrame.DISPOSE_ON_CLOSE)
                .setTitle("PBW - Cost Analysis");
    }

    private void populateCostAnalysisComboBoxes(CostAnalysisFrame costAnalysis) {
        HashMap<ResourceType, List<Resource>> boxOptions = new HashMap<>();
        // One combo box per resource type, so one list of options per box.
        for (ResourceType type : ResourceType.values()) {
            boxOptions.put(type, new ArrayList<>());
            // Add an empty resource 'None' by default.
            Resource defaultEmptyResource = new Resource("None");
            defaultEmptyResource.setType(type);
            boxOptions.get(type).add(defaultEmptyResource);
        }
        HashMap<String, Resource> resourceMap = (HashMap<String, Resource>) resourceManager.getResourceMap();
        // Sort the resources into the list corresponding to that resource's type.
        for (Resource resourceEntry : resourceMap.values()) {
            boxOptions.get(resourceEntry.getType()).add(resourceEntry);
        }
        // Send them to each relevant combo box
        costAnalysis.setBoardTypeOptions(boxOptions.get(ResourceType.BOARD));
        costAnalysis.setPaperTypeOptions(boxOptions.get(ResourceType.PAPER));
        costAnalysis.setGlueTypeOptions(boxOptions.get(ResourceType.GLUE));
        costAnalysis.setSpineTypeOptions(boxOptions.get(ResourceType.SPINE));
        costAnalysis.setThreadTypeOptions(boxOptions.get(ResourceType.THREAD));
        costAnalysis.setDecoratedPaperTypeOptions(boxOptions.get(ResourceType.DECORATED_PAPER));
        costAnalysis.setEndBandTypeOptions(boxOptions.get(ResourceType.END_BAND));
        costAnalysis.setSpiritsTypeOptions(boxOptions.get(ResourceType.MINERAL_SPIRIT));
    }


    public void mainEmployeeMenu() {
        employeeManager.fetchEmployees();
        eventManager.fetchEvents();
        EmployeeMenuFrame menu = new EmployeeMenuFrame();
        menu.setAddNewEmployeeAction(e -> addEmployee());
        menu.setAttendEventAction(e -> attendEvent());
        menu.setManageEventsAction(e -> manageEvents());
        menu.setTimeClockAction(e -> timeClock());
        menu.setViewEmployeesAction(e -> viewEmployees());
        showNewWindow(menu, JFrame.DISPOSE_ON_CLOSE)
                .setTitle("PBW - Employee");
    }

    public void timeClock() {
        TimeClockFrame clockFrame = new TimeClockFrame();
        clockFrame.setClockInOutAction(new ClockInOutListener(clockFrame, employeeManager));
        showNewWindow(clockFrame, JFrame.DISPOSE_ON_CLOSE)
                .setTitle("PBW - Clock");
    }


    public void addEmployee() {
        if (getConfirmation()) {
            employeeManager.fetchEmployees();
            AddEmployeeFrame addEmployeeFrame = new AddEmployeeFrame();

            addEmployeeFrame.setConfirmAction(e -> onAddNewEmployee(addEmployeeFrame));
            addEmployeeFrame.setCloseAction(e -> addEmployeeFrame.dispose());

            showNewWindow(addEmployeeFrame, JFrame.DISPOSE_ON_CLOSE)
                    .setTitle("PBW - Add Employee");
        }
    }

    private void onAddNewEmployee(AddEmployeeFrame addEmployeeFrame) {
        Employee employee = new Employee();
        employee.setPin(new PinNumber(addEmployeeFrame.getNewEmployeePin()));
        employee.setName(addEmployeeFrame.getNewEmployeeName());
        employee.setPoints(Integer.parseInt(addEmployeeFrame.getNewEmployeePoints()));
        employee.setLastClockInTime(LocalDateTime.now());
        employee.setLastClockOutTime(LocalDateTime.now());
        long hours = Long.parseLong(addEmployeeFrame.getNewEmployeeHours());
        employee.setWeeklyHours(Duration.ofHours(hours));
        employee.setTotalHours(Duration.ofHours(hours));

        employeeManager.newEmployee(employee);
        employeeManager.storeEmployees();
        addEmployeeFrame.clearFields();
    }

    public void attendEvent() {
        AttendEventFrame attendEventFrame = new AttendEventFrame();
        buildEventBox(attendEventFrame);
        attendEventFrame.setConfirmAction(new ConfirmAttendanceListener(attendEventFrame, employeeManager, eventManager));
        showNewWindow(attendEventFrame, JFrame.DISPOSE_ON_CLOSE)
                .setTitle("PBW - Event Attendance");
    }

    private void buildEventBox(AttendEventFrame attendEventFrame) {
        for (Event event : eventManager.getEventMap().values()) {
            attendEventFrame.addEventBoxItem(event.getEventCode());
        }

    }

    public void viewEmployees() {
        ManageEmployeesFrame manageEmployeesFrame = new ManageEmployeesFrame();
        JTable table = manageEmployeesFrame.getTable();
        if (getConfirmation()) {
            manageEmployeesFrame.setTableModel(makeEmployeeModel());
            manageEmployeesFrame.setNewRowAction(new AddEmployeeRowListener(table));
            manageEmployeesFrame.setRemoveRowAction(new RemoveRowListener(table));
            manageEmployeesFrame.setOKAction(new SaveEmployeeTableListener(manageEmployeesFrame, employeeManager));
            manageEmployeesFrame.setCancelAction(e -> manageEmployeesFrame.dispose());
            showNewWindow(manageEmployeesFrame, JFrame.DISPOSE_ON_CLOSE)
                    .setTitle("PBW - View Employee Infomration");
        }
    }

    private TableModel makeResourceModel() {
        DefaultTableModel model = new DefaultTableModel();
        HashMap<String, Resource> map = (HashMap<String, Resource>) resourceManager.getResourceMap();

        for (String header : ResourceIOPipe.CSV_FORMAT.getHeader()) {
            model.addColumn(header);
        }
        for (Resource resource : map.values()) {
            Vector<Object> newRow = new Vector<>();
            newRow.add(resource.getType());
            newRow.add(resource.getName());
            newRow.add(resource.getUnitSize());
            newRow.add(String.format("%.2f", resource.getPricePerUnit()));
            model.addRow(newRow);
        }

        return model;
    }

    private TableModel makeEmployeeModel() {
        DefaultTableModel employeeTableModel = new DefaultTableModel();
        employeeTableModel.addColumn("Pin");
        employeeTableModel.addColumn("Name");
        employeeTableModel.addColumn("Weekly Hours");
        employeeTableModel.addColumn("Total Hours");
        employeeTableModel.addColumn("Points");
        employeeTableModel.addColumn("Last Clock In");
        employeeTableModel.addColumn("Last Clock Out");
        HashMap<PinNumber, Employee> employeeMap = (HashMap<PinNumber, Employee>) employeeManager.getEmployeeMap();
        for (Employee employee : employeeMap.values()) {
            Vector<String> newRow = new Vector<>();
            newRow.add(employee.getPin().toString());
            newRow.add(employee.getName());
            newRow.add(employee.getWeeklyHours().toHoursPart() + ":" + employee.getWeeklyHours().toMinutesPart());
            newRow.add(employee.getTotalHours().toHoursPart() + ":" + employee.getTotalHours().toMinutesPart());
            newRow.add(String.valueOf(employee.getPoints()));
            newRow.add(employee.getLastClockInTime().toString());
            newRow.add(employee.getLastClockOutTime().toString());
            employeeTableModel.addRow(newRow);
        }

        return employeeTableModel;
    }

    public void manageEvents() {
        ManageEventsFrame manageEventsFrame = new ManageEventsFrame();
        JTable table = manageEventsFrame.getTable();
        if (getConfirmation()) {
            manageEventsFrame.setTableModel(makeEventModel(manageEventsFrame));
            manageEventsFrame.setNewRowAction(new AddEventRowListener(table));
            manageEventsFrame.setRemoveRowAction(new RemoveRowListener(table));
            manageEventsFrame.setCancelAction(e -> manageEventsFrame.dispose());
            manageEventsFrame.setConfirmAction(new SaveEventTableListener(manageEventsFrame, eventManager));
            showNewWindow(manageEventsFrame, JFrame.DISPOSE_ON_CLOSE)
                    .setTitle("PBW - Manage Event Information");
        }
    }

    private TableModel makeEventModel(ManageEventsFrame manageEventsFrame) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Code");
        model.addColumn("Name");
        model.addColumn("Worth");
        model.addColumn("Description");
        model.addColumn("Confirmation Code");
        HashMap<String, Event> eventMap = (HashMap<String, Event>) eventManager.getEventMap();
        for (Event event : eventMap.values()) {
            String code = event.getEventCode();
            int worth = event.getPointWorth();
            String name = event.getEventName();
            String desc = event.getEventDescription();
            String confirm = event.getEventConfirmationCode();
            Vector<String> newRow = new Vector<>();
            newRow.add(code);
            newRow.add(name);
            newRow.add(String.valueOf(worth));
            newRow.add(desc);
            newRow.add(confirm);
            model.addRow(newRow);
        }
        return model;
    }

    public boolean getConfirmation() {
        JPasswordField passwordField = new JPasswordField();
        JOptionPane.showConfirmDialog(null, passwordField, "Enter password to confirm:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        return String.valueOf(passwordField.getPassword()).hashCode() == 552231974;
    }
}
