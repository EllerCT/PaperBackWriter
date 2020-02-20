package listeners.cost_analysis;

import data_structures.Material;
import data_structures.ModularProduct;
import managers.ModularProductManager;
import swing_frames.CostAnalysisFrame;
import utilities.CostAnalyzer;
import utilities.Settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SubmitProductListener implements ActionListener {
    private CostAnalysisFrame costAnalysis;
    private ModularProductManager modularProductManager;

    public SubmitProductListener(CostAnalysisFrame costAnalysisFrame, ModularProductManager productManager) {
        this.modularProductManager = productManager;
        this.costAnalysis = costAnalysisFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new CalculateCostsListener(costAnalysis, new CostAnalyzer()).actionPerformed(null);
        ModularProduct product = new ModularProduct();
        product.setId(costAnalysis.getIdNumber());
        product.setName(costAnalysis.getName());
        product.setDate(costAnalysis.getDate());
        product.setDescription(costAnalysis.getProductDescription());
        product.setType(costAnalysis.getProductType());
        product.setTotalCost(costAnalysis.getTotalCost());
        ArrayList<Material> materials = new ArrayList<>(costAnalysis.getMaterials());
        product.setMaterials(materials);
        modularProductManager.add(product);
        modularProductManager.store();
        Integer updatedID = Integer.parseInt(ModularProduct.getCurrentID()) + 1;
        Settings.getInstance().store("currentID", updatedID.toString());
        ModularProduct.setCurrentID(updatedID.toString());
        Settings.getInstance().save();
        costAnalysis.dispose();
    }
}
