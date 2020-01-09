import data_structures.*;
import io_pipes.ResourceIOPipe;
import managers.EmployeeManager;
import managers.EventManager;
import managers.ResourceManager;
import managers.TimeClockManager;
import swing_frames.*;

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

    /**
     * Open a new window with given content and close behavior.
     *
     * @param contentPanel  A JPanel representing a content panel
     * @param closeBehavior An integer such as JFrame.DISPOSE_ON_CLOSE
     */
    private void show(JPanel contentPanel, int closeBehavior){
        JFrame frame = new JFrame();
        frame.setContentPane(contentPanel);
        frame.setDefaultCloseOperation(closeBehavior);
        frame.pack();
        frame.setVisible(true);
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
        show(mainMenu.getContentPane(), JFrame.EXIT_ON_CLOSE);
    }

    private void mainProductsMenu() {
        resourceManager.fetchResources();
        ProductsMenuFrame menu = new ProductsMenuFrame();
        // TODO: Add means of browsing complete products.
        // menu.setBrowseProductsButtonListener();
        menu.setCostAnalysisButtonListener(e -> costAnalysis());
        menu.setResourcesButtonListener(e -> resources());
        show(menu.getPane(), JFrame.DISPOSE_ON_CLOSE);
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
        show(resources.getPanel(), JFrame.DISPOSE_ON_CLOSE);
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
            int stocked = Integer.parseInt((String) row.get(4));

            Resource resource = new Resource(name);
            resource.setType(type);
            resource.setUnitSize(unitSize);
            resource.setPricePerUnit(unitPrice);
            resource.setUnitsInStock(stocked);
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

    private void costAnalysis() {
        CostAnalysisFrame costAnalysis = new CostAnalysisFrame();
        CostAnalyser analyser = new CostAnalyser();
        // Populate combo boxes.
        populateCostAnalysisComboBoxes(costAnalysis);

        // Set button behavior
        costAnalysis.setCalculateButtonAction(e -> calculateCosts(costAnalysis, analyser));
        costAnalysis.setCancelButtonAction(e -> closePanel(costAnalysis.getPanel()));
        costAnalysis.setSubmitButtonAction(e -> JOptionPane.showMessageDialog(null, "Storage of products not currently supported."));

        show(costAnalysis.getPanel(), JFrame.DISPOSE_ON_CLOSE);
    }

    private void populateCostAnalysisComboBoxes(CostAnalysisFrame costAnalysis) {
        HashMap<ResourceType, List<Resource>> boxOptions = new HashMap<>();
        // One combo box per resource type, so one list of options per box.
        for (ResourceType type : ResourceType.values()) {
            boxOptions.put(type, new ArrayList<>());
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
        if (costAnalysis.getOtherCost().isEmpty() || !costAnalysis.getOtherCost().matches("(0-9)*")) {
            otherCost = 0.0;
        } else {
            otherCost = Double.parseDouble(costAnalysis.getOtherCost());
        }

        List<Double> subtotals = Arrays.asList(
                paperCost,
                spineCost,
                boardCost,
                glueCost,
                threadCost,
                endBandCost,
                decorativePaperCost,
                otherCost);

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
        show(menu.getPanel(), JFrame.DISPOSE_ON_CLOSE);
    }

    public void timeClock() {
        TimeClockFrame clockFrame = new TimeClockFrame();
        clockFrame.setClockInOutAction(e -> clockInOut(clockFrame));
        show(clockFrame.getPanel(), JFrame.DISPOSE_ON_CLOSE);
    }

    private void clockInOut(TimeClockFrame clockFrame) {
        TimeClockManager clock = new TimeClockManager();
        if (!clockFrame.getPin().isBlank()){
            PinNumber pin = new PinNumber(clockFrame.getPin());
            Employee matchingEmployee = employeeManager.getEmployee(pin);
            if(matchingEmployee != null){
                boolean clockedIn = clock.checkIfClockedIn(matchingEmployee);
                if (clockedIn){
                    clock.clockOut(matchingEmployee);
                    JOptionPane.showMessageDialog(null, pin + " Clocked out.");
                } else {
                    clock.clockIn(matchingEmployee);
                    JOptionPane.showMessageDialog(null, pin + " Clocked in.");
                }
                employeeManager.updateEmployee(matchingEmployee);
                employeeManager.storeEmployees();
            } else {
                JOptionPane.showMessageDialog(null, "Unknown Pin");
            }
            clockFrame.clearPin();
        }
    }

    public void addEmployee() {
        if (getConfirmation()) {
            employeeManager.fetchEmployees();
            AddEmployeeFrame addEmployeeFrame = new AddEmployeeFrame();

            addEmployeeFrame.setConfirmAction(e -> onAddNewEmployee(addEmployeeFrame));
            addEmployeeFrame.setCloseAction(e -> ((JFrame) addEmployeeFrame.getPanel().getRootPane().getParent()).dispose());

            show(addEmployeeFrame.getPanel(), JFrame.DISPOSE_ON_CLOSE);
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
        show(attendEventFrame.getPanel(), JFrame.DISPOSE_ON_CLOSE);
    }

    private void onConfirmAttend(AttendEventFrame attendEventFrame) {
        Event selected = eventManager.getEvent(attendEventFrame.getSelectedEvent());
        if (!attendEventFrame.getPin().isBlank()) {
            PinNumber pin = new PinNumber(attendEventFrame.getPin());
            Employee employee = employeeManager.getEmployee(pin);
            if (employee != null && getConfirmation()) {
                int pointTotal = employee.getPoints() + selected.getPointWorth();
                employee.setPoints(pointTotal);
                employeeManager.updateEmployee(employee);
                JOptionPane.showMessageDialog(null, "Confirmed attendance of " + employee.getName() + " to event: " + selected.getEventName());
            }
            attendEventFrame.clearPin();
        }
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
            show(manageEmployeesFrame.getPanel(), JFrame.DISPOSE_ON_CLOSE);

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
            newRow.add(String.format("%d", resource.getUnitsInStock()));
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
            show(manageEventsFrame.getPanel(), JFrame.DISPOSE_ON_CLOSE);
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
            Event event = new Event(code, worth);
            event.setEventName(name);
            event.setEventDescription(desc);
            newEventMap.put(code, event);
        }
        eventManager.setEventMap(newEventMap);
        eventManager.storeEvents();
        closePanel(manageEventsFrame.getPanel());
    }

    private void newEventTableRow(ManageEventsFrame frame) {
        DefaultTableModel table = (DefaultTableModel) frame.getModel();
        table.addRow(new String[]{"NewCode", "", "0", ""});
    }

    private TableModel makeEventModel(ManageEventsFrame manageEventsFrame) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Code");
        model.addColumn("Name");
        model.addColumn("Worth");
        model.addColumn("Description");
        HashMap<String, Event> eventMap = (HashMap<String, Event>) eventManager.getEventMap();
        for (Event event : eventMap.values()) {
            String code = event.getEventCode();
            int worth = event.getPointWorth();
            String name = event.getEventName();
            String desc = event.getEventDescription();
            Vector<String> newRow = new Vector<>();
            newRow.add(code);
            newRow.add(name);
            newRow.add(String.valueOf(worth));
            newRow.add(desc);
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
