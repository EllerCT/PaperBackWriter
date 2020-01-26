package listeners.cost_analysis;

import swing_frames.CostAnalysisFrame;
import utilities.CostAnalyzer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class CalculateCostsListener implements ActionListener {

    private CostAnalyzer analyzer;
    private CostAnalysisFrame costAnalysis;

    public CalculateCostsListener(CostAnalysisFrame costAnalysis, CostAnalyzer analyzer) {
        this.analyzer = analyzer;
        this.costAnalysis = costAnalysis;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        double paperCost = analyzer.calculateSingleCostFor(
                costAnalysis.getPaperUnits(),
                costAnalysis.getCurrentPaperType());
        costAnalysis.setPaperCost(String.format("%.2f", paperCost));

        double boardCost = analyzer.calculateSingleCostFor(
                costAnalysis.getBoardUnits(),
                costAnalysis.getCurrentBoardType());
        costAnalysis.setBoardCost(String.format("%.2f", boardCost));

        double threadCost = analyzer.calculateSingleCostFor(
                costAnalysis.getThreadUnits(),
                costAnalysis.getCurrentThreadType());
        costAnalysis.setThreadCost(String.format("%.2f", threadCost));

        double glueCost = analyzer.calculateSingleCostFor(
                costAnalysis.getGlueUnits(),
                costAnalysis.getCurrentGlueType());
        costAnalysis.setGlueCost(String.format("%.2f", glueCost));

        double decorativePaperCost = analyzer.calculateSingleCostFor(
                costAnalysis.getDecoratedPaperUnits(),
                costAnalysis.getCurrentDecoratedPaperType());
        costAnalysis.setDecoratedPaperCost(String.format("%.2f", decorativePaperCost));

        double endBandCost = analyzer.calculateSingleCostFor(
                costAnalysis.getEndBandUnits(),
                costAnalysis.getCurrentEndBandType());
        costAnalysis.setEndBandCost(String.format("%.2f", endBandCost));

        double spineCost = analyzer.calculateSingleCostFor(
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

        double spiritsCost = analyzer.calculateSingleCostFor(
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

        double totalCost = analyzer.calculateTotalCostFromSubtotals(subtotals);
        costAnalysis.setTotalCost(totalCost);
    }
}
