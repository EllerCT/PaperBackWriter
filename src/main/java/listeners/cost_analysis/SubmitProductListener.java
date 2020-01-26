package listeners.cost_analysis;

import data_structures.Product;
import managers.ProductManager;
import swing_frames.CostAnalysisFrame;
import utilities.CostAnalyzer;
import utilities.Settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubmitProductListener implements ActionListener {
    private CostAnalysisFrame costAnalysis;
    private ProductManager productManager;

    public SubmitProductListener(CostAnalysisFrame costAnalysisFrame, ProductManager productManager) {
        this.productManager = productManager;
        this.costAnalysis = costAnalysisFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new CalculateCostsListener(costAnalysis, new CostAnalyzer()).actionPerformed(null);
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
        costAnalysis.dispose();
    }
}
