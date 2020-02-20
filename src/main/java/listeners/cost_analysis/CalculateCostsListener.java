package listeners.cost_analysis;

import swing_frames.CostAnalysisFrame;
import utilities.CostAnalyzer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CalculateCostsListener implements ActionListener {

    private CostAnalyzer analyzer;
    private CostAnalysisFrame costAnalysis;

    public CalculateCostsListener(CostAnalysisFrame costAnalysis, CostAnalyzer analyzer) {
        this.analyzer = analyzer;
        this.costAnalysis = costAnalysis;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<Double> costs = costAnalysis.getMaterialCosts();
        Double totalCost = analyzer.calculateTotalCostFromSubtotals(costs);
        costAnalysis.setTotalCost(totalCost);
    }
}
