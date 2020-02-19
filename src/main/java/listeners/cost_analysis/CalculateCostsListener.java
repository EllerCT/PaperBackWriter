package listeners.cost_analysis;

import swing_frames.CostAnalysisFrame;
import utilities.CostAnalyzer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculateCostsListener implements ActionListener {

    private CostAnalyzer analyzer;
    private CostAnalysisFrame costAnalysis;

    public CalculateCostsListener(CostAnalysisFrame costAnalysis, CostAnalyzer analyzer) {
        this.analyzer = analyzer;
        this.costAnalysis = costAnalysis;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO: Implement
    }
}
