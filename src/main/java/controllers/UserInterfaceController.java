package controllers;

import data_structures.*;
import io_pipes.ResourceIOPipe;
import listeners.time_clock.ClockInOutListener;
import managers.EmployeeManager;
import managers.EventManager;
import managers.ProductManager;
import managers.ResourceManager;
import swing_frames.*;
import utilities.CostAnalyser;
import utilities.Settings;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class UserInterfaceController {
    private EmployeeManager employeeManager;
    private EventManager eventManager;
    private ResourceManager resourceManager;
    private ProductManager productManager;

    /**
     * Open a new window with given content and close behavior.
     *
     * @param contentPanel  A JPanel representing a content panel
     * @param closeBehavior An integer such as JFrame.DISPOSE_ON_CLOSE
     * @return - The JFrame created
     */
    public static JFrame showNewWindow(JPanel contentPanel, int closeBehavior) {
        JFrame frame = new JFrame();
        frame.setContentPane(contentPanel);
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

    public void setEmployeeManager(EmployeeManager manager){
        this.employeeManager = manager;
    }

    public void setEventManager(EventManager manager) {
        this.eventManager = manager;
    }

    public void mainMenu() {
        MainMenuFrame mainMenu = new MainMenuFrame();
        mainMenu.setEmployeeButtonAction(e -> mainEmployeeMenu());
        mainMenu.setProductButtonAction(e -> mainProductsMenu());
        showNewWindow(mainMenu.getContentPane(), JFrame.EXIT_ON_CLOSE)
                .setTitle("Paper Back Writer");
    }

    private void mainProductsMenu() {
        resourceManager.fetchResources();
        ProductsMenuFrame menu = new ProductsMenuFrame();
        menu.setBrowseProductsButtonListener(e -> productBrowser());
        menu.setCostAnalysisButtonListener(e -> costAnalysis());
        menu.setResourcesButtonListener(e -> resources());
        showNewWindow(menu.getPane(), JFrame.DISPOSE_ON_CLOSE)
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
        resources.setSaveButtonListener(e -> saveResourcesTable(resources));
        resources.setAddButtonListener(e -> addRowToResources(resources));
        resources.setRemoveButtonListener(e -> removeRowsFrom(table));
        resources.setCancelButtonListener(e -> closePanel(resources.getPanel()));
        showNewWindow(resources.getPanel(), JFrame.DISPOSE_ON_CLOSE)
                .setTitle("PBW Resources");
    }

    private void saveResourcesTable(ResourcesFrame resourcesFrame) {
        DefaultTableModel model = (DefaultTableModel) resourcesFrame.getModel();
        Vector<Vector> rows = model.getDataVector();
        HashMap<String, Resource> resourceMap = new HashMap<>();
        for (Vector row : rows) {
            ResourceType type = (ResourceType) row.get(0);
            String name = (String) row.get(1);
            String unitSize = (String) row.get(2);
            double unitPrice = Double.parseDouble((String) row.get(3));

            Resource resource = new Resource(name);
            resource.setType(type);
            resource.setUnitSize(unitSize);
            resource.setPricePerUnit(unitPrice);
            resourceMap.put(Resource.generateKeyFor(resource), resource);
        }
        resourceManager.setResourceMap(resourceMap);
        resourceManager.storeResources();
        closePanel(resourcesFrame.getPanel());
    }

    private void addRowToResources(ResourcesFrame resourcesFrame) {
        DefaultTableModel tableModel = (DefaultTableModel) resourcesFrame.getModel();
        tableModel.addRow(new Object[]{ResourceType.PAPER, "", "", 0.0, 0});
    }

    private void productBrowser() {
        productManager.fetchProducts();
        ProductBrowserFrame productBrowser = new ProductBrowserFrame();
        productBrowser.setCloseButtonListener(e -> closePanel(productBrowser.getContentPanel()));
        productBrowser.setViewButtonListener(e -> viewProduct(productBrowser));
        productBrowser.setEnableGradingButtonListener(e -> enableGrading(productBrowser));

        DefaultTableModel model = makeProductModel();
        productBrowser.setProductsTableModel(model);

        showNewWindow(productBrowser.getContentPanel(), JFrame.DISPOSE_ON_CLOSE)
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
        viewer.setOkButtonListener(e -> onProductViewerOK(grading, selectedProduct, viewer));
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

        showNewWindow(viewer.getPanel(), JFrame.DISPOSE_ON_CLOSE)
                .setTitle("PBW - Product Viewer");
    }

    private void onProductViewerOK(boolean grading, Product selected, ProductViewerFrame viewer) {
        if (!grading) {
            closePanel(viewer.getPanel());
        } else {
            selected.setGrade(viewer.getGrade());
            productManager.updateProduct(selected);
            productManager.storeProducts();
            closePanel(viewer.getPanel());
        }
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
        CostAnalyser analyser = new CostAnalyser();
        costAnalysis.setIdNumber(Product.getCurrentID());
        // Populate combo boxes.
        populateCostAnalysisComboBoxes(costAnalysis);

        // Set button behavior
        costAnalysis.setCalculateButtonAction(e -> calculateCosts(costAnalysis, analyser));
        costAnalysis.setCancelButtonAction(e -> closePanel(costAnalysis.getPanel()));
        costAnalysis.setSubmitButtonAction(e -> saveNewProduct(costAnalysis));

        showNewWindow(costAnalysis.getPanel(), JFrame.DISPOSE_ON_CLOSE)
                .setTitle("PBW - Cost Analysis");
    }

    private void saveNewProduct(CostAnalysisFrame costAnalysis) {
        calculateCosts(costAnalysis, new CostAnalyser());
        Product product = new Product();
        product.setId(costAnalysis.getIdNumber());
        product.setName(costAnalysis.getName());
        product.setType(costAnalysis.getProductType());
        product.setDate(costAnalysis.getDate());
        product.setDescription(costAnalysis.getProductDescription());
        product.setGrade(costAnalysis.getGrade());
        product.setTotalCost(costAnalysis.getTotalCost());
        product.setPaperType(costAnalysis.getCurrentPaperType().toString());
        product.setPaperAmount(String.valueOf(costAnalysis.getPaperUnits()));
        product.setPaperCost(costAnalysis.getPaperCost());
        product.setThreadType(costAnalysis.getCurrentThreadType().toString());
        product.setThreadAmount(String.valueOf(costAnalysis.getThreadUnits()));
        product.setThreadCost(costAnalysis.getThreadCost());
        product.setGlueType(costAnalysis.getCurrentGlueType().toString());
        product.setGlueAmount(String.valueOf(costAnalysis.getGlueUnits()));
        product.setGlueCost(costAnalysis.getGlueCost());
        product.setBoardType(costAnalysis.getCurrentBoardType().toString());
        product.setBoardAmount(String.valueOf(costAnalysis.getGlueUnits()));
        product.setBoardCost(costAnalysis.getBoardCost());
        product.setDecoratedPaperType(costAnalysis.getCurrentDecoratedPaperType().toString());
        product.setDecoratedPaperAmount(String.valueOf((costAnalysis.getDecoratedPaperUnits())));
        product.setDecoratedPaperCost(costAnalysis.getDecoratedPaperCost());
        product.setSpineType(costAnalysis.getCurrentSpineType().toString());
        product.setSpineAmount(String.valueOf(costAnalysis.getSpineUnits()));
        product.setSpineCost(costAnalysis.getSpineCost());
        product.setEndBandType(costAnalysis.getCurrentEndBandType().toString());
        product.setEndBandAmount(String.valueOf(costAnalysis.getEndBandUnits()));
        product.setEndBandCost(costAnalysis.getEndBandCost());
        product.setOther(costAnalysis.getOtherMaterial());
        product.setOtherAmount(String.valueOf(costAnalysis.getOtherUnits()));
        product.setOtherCost(costAnalysis.getOtherCost());
        product.setSpiritType(costAnalysis.getCurrentSpiritsType().toString());
        product.setSpiritAmount(String.valueOf(costAnalysis.getSpiritsUnits()));
        product.setSpiritCost(costAnalysis.getSpiritsCost());
        productManager.newProduct(product);
        productManager.storeProducts();
        String newCurrentID = String.valueOf(Integer.parseInt(Product.getCurrentID()) + 1);
        Product.setCurrentID(newCurrentID);
        // TODO: Figure out how to handle this better
        Settings.store("currentID", newCurrentID);
        Settings.save();
        closePanel(costAnalysis.getPanel());
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

    private void calculateCosts(CostAnalysisFrame costAnalysis, CostAnalyser analyser) {
        double paperCost = analyser.calculateSingleCostFor(
                costAnalysis.getPaperUnits(),
                costAnalysis.getCurrentPaperType());
        costAnalysis.setPaperCost(String.format("%.2f", paperCost));

        double boardCost = analyser.calculateSingleCostFor(
                costAnalysis.getBoardUnits(),
                costAnalysis.getCurrentBoardType());
        costAnalysis.setBoardCost(String.format("%.2f", boardCost));

        double threadCost = analyser.calculateSingleCostFor(
                costAnalysis.getThreadUnits(),
                costAnalysis.getCurrentThreadType());
        costAnalysis.setThreadCost(String.format("%.2f", threadCost));

        double glueCost = analyser.calculateSingleCostFor(
                costAnalysis.getGlueUnits(),
                costAnalysis.getCurrentGlueType());
        costAnalysis.setGlueCost(String.format("%.2f", glueCost));

        double decorativePaperCost = analyser.calculateSingleCostFor(
                costAnalysis.getDecoratedPaperUnits(),
                costAnalysis.getCurrentDecoratedPaperType());
        costAnalysis.setDecoratedPaperCost(String.format("%.2f", decorativePaperCost));

        double endBandCost = analyser.calculateSingleCostFor(
                costAnalysis.getEndBandUnits(),
                costAnalysis.getCurrentEndBandType());
        costAnalysis.setEndBandCost(String.format("%.2f", endBandCost));

        double spineCost = analyser.calculateSingleCostFor(
                costAnalysis.getSpineUnits(),
                costAnalysis.getCurrentSpineType());
        costAnalysis.setSpineCost(String.format("%.2f", spineCost));

        // 'other' costs is a manual field to account for the unaccountable.
        // There is no 'other' resource fitting the pattern for the analyser.
        // This should be a formatted text field but at present I can't wrangle
        // one into working. Doing so would remove all logic from this initialization.
        // TODO: Introduce formatted text field to remove need for checks in controller.
        double otherCost;
        if (costAnalysis.getOtherCost().isEmpty() || !costAnalysis.getOtherCost().matches(".*[0-9]*.*")) {
            otherCost = 0.0;
        } else {
            otherCost = Double.parseDouble(costAnalysis.getOtherCost());
        }

        double spiritsCost = analyser.calculateSingleCostFor(
                costAnalysis.getSpiritsUnits(),
                costAnalysis.getCurrentSpiritsType());
        costAnalysis.setSpiritsCost(String.format("%.2f", spineCost));

        List<Double> subtotals = Arrays.asList(
                paperCost,
                spineCost,
                boardCost,
                glueCost,
                threadCost,
                endBandCost,
                decorativePaperCost,
                otherCost,
                spiritsCost);

        double totalCost = analyser.calculateTotalCostFromSubtotals(subtotals);
        costAnalysis.setTotalCost(totalCost);
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
        showNewWindow(menu.getPanel(), JFrame.DISPOSE_ON_CLOSE)
                .setTitle("PBW - Employee");
    }

    public void timeClock() {
        TimeClockFrame clockFrame = new TimeClockFrame();
        clockFrame.setClockInOutAction(new ClockInOutListener(clockFrame, employeeManager));
        showNewWindow(clockFrame.getPanel(), JFrame.DISPOSE_ON_CLOSE)
                .setTitle("PBW - Clock");
    }


    public void addEmployee() {
        if (getConfirmation()) {
            employeeManager.fetchEmployees();
            AddEmployeeFrame addEmployeeFrame = new AddEmployeeFrame();

            addEmployeeFrame.setConfirmAction(e -> onAddNewEmployee(addEmployeeFrame));
            addEmployeeFrame.setCloseAction(e -> ((JFrame) addEmployeeFrame.getPanel().getRootPane().getParent()).dispose());

            showNewWindow(addEmployeeFrame.getPanel(), JFrame.DISPOSE_ON_CLOSE)
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
        attendEventFrame.setConfirmAction(e -> onConfirmAttend(attendEventFrame));
        showNewWindow(attendEventFrame.getPanel(), JFrame.DISPOSE_ON_CLOSE)
                .setTitle("PBW - Event Attendance");
    }

    private void onConfirmAttend(AttendEventFrame attendEventFrame) {
        Event selected = eventManager.getEvent(attendEventFrame.getSelectedEvent());
        if (!attendEventFrame.getPin().isBlank()) {
            PinNumber pin = new PinNumber(attendEventFrame.getPin());
            Employee employee = employeeManager.getEmployee(pin);
            if (employee != null) {
                if (selected.getEventConfirmationCode().isBlank()) {
                    System.out.println(selected.getEventConfirmationCode());
                    creditEvent(selected, employee);
                } else {
                    String code = JOptionPane.showInputDialog("Please enter the confirmation code for this event.");
                    if (code == null) code = "";
                    if (code.equals(selected.getEventConfirmationCode()))
                        creditEvent(selected, employee);
                    else JOptionPane.showMessageDialog(null, "That code was incorrect.");
                }
            }
            attendEventFrame.clearPin();
        }
    }

    private void creditEvent(Event selected, Employee employee) {
        int pointTotal = employee.getPoints() + selected.getPointWorth();
        employee.setPoints(pointTotal);
        employeeManager.updateEmployee(employee);
        JOptionPane.showMessageDialog(null, "Confirmed attendance of " + employee.getName() + " to event: " + selected.getEventName());
    }

    private void buildEventBox(AttendEventFrame attendEventFrame) {
        for (Event event : eventManager.getEventMap().values()) {
            attendEventFrame.addEventBoxItem(event.getEventCode());
        }

    }

    public void viewEmployees() {
        ManageEmployeesFrame manageEmployeesFrame = new ManageEmployeesFrame();
        if (getConfirmation()) {
            manageEmployeesFrame.setTableModel(makeEmployeeModel());
            manageEmployeesFrame.setNewRowAction(e -> newEmployeeTableRow(manageEmployeesFrame));
            manageEmployeesFrame.setRemoveRowAction(e -> removeRowsFrom(manageEmployeesFrame.getTable()));
            manageEmployeesFrame.setOKAction(e -> saveEmployeesTable(manageEmployeesFrame));
            manageEmployeesFrame.setCancelAction(e -> closePanel(manageEmployeesFrame.getPanel()));
            showNewWindow(manageEmployeesFrame.getPanel(), JFrame.DISPOSE_ON_CLOSE)
                    .setTitle("PBW - View Employee Infomration");
        }
    }

    private void removeRowsFrom(JTable table) {
        if (JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION) == 0) {
            int[] rows = table.getSelectedRows();
            Arrays.sort(rows); // So the index shrinks from last first.
            for (int i = rows.length - 1; i >= 0; i--) {
                ((DefaultTableModel) table.getModel()).removeRow(i);
            }
        }
    }

    private void newEmployeeTableRow(ManageEmployeesFrame manageEmployeesFrame) {
        DefaultTableModel table = (DefaultTableModel) manageEmployeesFrame.getModel();
        String now = LocalDateTime.now().toString();
        table.addRow(new String[]{"", "", "0:0", "0:0", "0", now, now});
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

    private void closePanel(JPanel panel) {
        ((JFrame) panel.getRootPane().getParent()).dispose();
    }

    private void saveEmployeesTable(ManageEmployeesFrame manageEmployeesFrame) {
        DefaultTableModel tableModel = (DefaultTableModel) manageEmployeesFrame.getModel();
        Vector<Vector> rows = tableModel.getDataVector();
        HashMap<PinNumber, Employee> newMap = new HashMap<>();
        for (Vector<String> row : rows) {
            Employee newEmployee = new Employee();
            PinNumber pin = new PinNumber(row.get(0));
            newEmployee.setPin(pin);
            String name = row.get(1);
            newEmployee.setName(name);
            String hoursPart = row.get(2).split(":")[0];
            String minutesPart = row.get(2).split(":")[1];
            Duration weeklyHours = Duration.ofHours(Integer.parseInt(hoursPart)).plusMinutes(Integer.parseInt(minutesPart));
            newEmployee.setWeeklyHours(weeklyHours);
            hoursPart = row.get(3).split(":")[0];
            minutesPart = row.get(3).split(":")[1];
            Duration totalHours = Duration.ofHours(Integer.parseInt(hoursPart)).plusMinutes(Integer.parseInt(minutesPart));
            newEmployee.setTotalHours(totalHours);
            int points = Integer.parseInt(row.get(4));
            newEmployee.setPoints(points);
            newEmployee.setLastClockInTime(LocalDateTime.parse(row.get(5)));
            newEmployee.setLastClockOutTime(LocalDateTime.parse(row.get(6)));
            newMap.put(pin, newEmployee);
        }
        employeeManager.setEmployeeMap(newMap);
        employeeManager.storeEmployees();
        closePanel(manageEmployeesFrame.getPanel());
    }

    public void manageEvents() {
        ManageEventsFrame manageEventsFrame = new ManageEventsFrame();

        if (getConfirmation()) {
            manageEventsFrame.setTableModel(makeEventModel(manageEventsFrame));
            manageEventsFrame.setNewRowAction(e -> newEventTableRow(manageEventsFrame));
            manageEventsFrame.setRemoveRowAction(e -> removeRowsFrom(manageEventsFrame.getTable()));
            manageEventsFrame.setCancelAction(e -> closePanel(manageEventsFrame.getPanel()));
            manageEventsFrame.setConfirmAction(e -> saveEventsTable(manageEventsFrame));
            showNewWindow(manageEventsFrame.getPanel(), JFrame.DISPOSE_ON_CLOSE)
                    .setTitle("PBW - Manage Event Information");
        }
    }

    private void saveEventsTable(ManageEventsFrame manageEventsFrame) {
        DefaultTableModel tableModel = (DefaultTableModel) manageEventsFrame.getModel();
        Vector<Vector> rows = tableModel.getDataVector();
        HashMap<String, Event> newEventMap = new HashMap<>();
        for (Vector<String> row : rows) {
            String code = row.get(0);
            String name = row.get(1);
            int worth = Integer.parseInt(row.get(2));
            String desc = row.get(3);
            String confirmation = row.get(4);
            Event event = new Event(code, worth);
            event.setEventName(name);
            event.setEventDescription(desc);
            event.setEventConfirmationCode(confirmation);
            newEventMap.put(code, event);
        }
        eventManager.setEventMap(newEventMap);
        eventManager.storeEvents();
        closePanel(manageEventsFrame.getPanel());
    }

    private void newEventTableRow(ManageEventsFrame frame) {
        DefaultTableModel table = (DefaultTableModel) frame.getModel();
        table.addRow(new String[]{"NewCode", "", "0", "", ""});
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
