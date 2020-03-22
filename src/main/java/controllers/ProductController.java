package controllers;

import data_structures.Material;
import data_structures.ModularProduct;
import data_structures.ResourceType;
import listeners.cost_analysis.AddMaterialListener;
import listeners.cost_analysis.CalculateCostsListener;
import listeners.cost_analysis.SubmitProductListener;
import listeners.general.RemoveRowListener;
import listeners.product_browser.OpenViewProductListener;
import listeners.product_viewer.ViewerFinishedListener;
import listeners.resource_browser.AddResourceRowListener;
import listeners.resource_browser.SaveResourceTableListener;
import managers.ModularProductManager;
import managers.ResourceManager;
import swing_frames.CostAnalysisFrame;
import swing_frames.ProductBrowserFrame;
import swing_frames.ProductViewerFrame;
import swing_frames.ResourcesFrame;
import ui_components.ReadOnlyMaterialPane;
import ui_components.table_models.ProductTableModel;
import ui_components.table_models.ResourceTableModel;
import utilities.CostAnalyzer;
import utilities.Security;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ProductController {

    private ModularProductManager modularProductManager;
    private ResourceManager resourceManager;

    public ProductController(ModularProductManager modularProductManager, ResourceManager resourceManager) {
        this.modularProductManager = modularProductManager;
        modularProductManager.fetch();
        this.resourceManager = resourceManager;
        resourceManager.fetch();
    }

    public ResourcesFrame resources() {
        ResourcesFrame resources = new ResourcesFrame();
        JTable table = resources.getTable();
        table.setModel(new ResourceTableModel(resourceManager.getMap()));
        // Make a combo box and populate it with the types of resources.
        JComboBox<ResourceType> resourceTypeDropdown = new JComboBox<>(ResourceType.values());
        // Use that combo box to edit cells in the 'Type' column.
        table.getColumn("Type").setCellEditor(new DefaultCellEditor(resourceTypeDropdown));
        resources.setSaveButtonListener(new SaveResourceTableListener(resources, resourceManager));
        resources.setAddButtonListener(new AddResourceRowListener(table));
        resources.setRemoveButtonListener(new RemoveRowListener(table));
        resources.setCancelButtonListener(e -> resources.dispose());
        return resources;
    }

    public ProductBrowserFrame productBrowser(DesktopController desktopController) {
        ProductBrowserFrame productBrowser = new ProductBrowserFrame();
        productBrowser.setCloseButtonListener(e -> productBrowser.dispose());
        productBrowser.setViewButtonListener(new OpenViewProductListener(desktopController, this, productBrowser));
        productBrowser.setEnableGradingButtonListener(e -> enableGrading(productBrowser));

        DefaultTableModel model = new ProductTableModel(modularProductManager.getMap());
        productBrowser.setProductsTableModel(model);

        return productBrowser;
    }

    private void enableGrading(ProductBrowserFrame browser) {
        if (new Security().getAuthorization()) {
            browser.enableGradingCheckbox();
        }
    }

    public ProductViewerFrame viewProduct(ProductBrowserFrame productBrowser) {
        boolean grading = productBrowser.isGrading();
        int selectedRow = productBrowser.getProductsTable().getSelectedRow();
        String id = (String) productBrowser.getProductsTableModel().getValueAt(selectedRow, 0);
        ModularProduct selectedProduct = (ModularProduct) modularProductManager.getFromKey(id);

        ProductViewerFrame viewer = new ProductViewerFrame();
        viewer.setOkButtonListener(new ViewerFinishedListener(viewer, modularProductManager, grading, selectedProduct));
        fillViewerFields(viewer, selectedProduct);

        return viewer;
    }


    private void fillViewerFields(ProductViewerFrame viewer, ModularProduct selectedProduct) {
        viewer.setIdNumber(selectedProduct.getId());
        viewer.setName(selectedProduct.getName());
        viewer.setProductType(selectedProduct.getType());
        viewer.setDate(selectedProduct.getDate());
        viewer.setProductDescription(selectedProduct.getDescription());
        viewer.setGrade(selectedProduct.getGrade());
        viewer.setTotalCost(selectedProduct.getTotalCost());
        for (Material material : selectedProduct.getMaterials()) {
            ReadOnlyMaterialPane materialPane = new ReadOnlyMaterialPane(material);
            viewer.addReadOnlyMaterialPane(materialPane);
        }
    }

    public CostAnalysisFrame costAnalysis() {
        modularProductManager.fetch(); // Maybe unnecessary but better safe.
        CostAnalysisFrame costAnalysis = new CostAnalysisFrame();
        CostAnalyzer analyser = new CostAnalyzer();
        costAnalysis.setIdNumber(ModularProduct.getCurrentID());

        // Set button behavior
        costAnalysis.setCalculateButtonAction(new CalculateCostsListener(costAnalysis, analyser));
        costAnalysis.setCancelButtonAction(e -> costAnalysis.dispose());
        costAnalysis.setSubmitButtonAction(new SubmitProductListener(costAnalysis, modularProductManager));
        costAnalysis.setAddMaterialButtonAction(new AddMaterialListener(costAnalysis, resourceManager.getMap(), new CostAnalyzer()));

        return costAnalysis;
    }
}
