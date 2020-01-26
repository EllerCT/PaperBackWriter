package listeners.cost_analysis;

import data_structures.Product;
import managers.ProductManager;
import swing_frames.CostAnalysisFrame;
import utilities.CostAnalyzer;
import utilities.Settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class SubmitProductListener implements ActionListener {
    private CostAnalysisFrame costAnalysis;
    private ProductManager productManager;

    public SubmitProductListener(CostAnalysisFrame costAnalysisFrame, ProductManager productManager) {
        this.productManager = productManager;
        this.costAnalysis = costAnalysisFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        calculateCosts(costAnalysis, new CostAnalyzer());
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

    //TODO: Remove duplicate code... somehow
    private void calculateCosts(CostAnalysisFrame costAnalysis, CostAnalyzer analyser) {
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
}
