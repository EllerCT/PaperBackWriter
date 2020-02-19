package listeners.cost_analysis;

import managers.ProductManager;
import swing_frames.CostAnalysisFrame;
import utilities.CostAnalyzer;

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
        // TODO: Implement
    }
}
