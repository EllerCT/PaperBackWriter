package listeners.cost_analysis;

import swing_frames.CostAnalysisFrame;
import ui_components.MaterialPane;
import utilities.CostAnalyzer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class AddMaterialListener implements ActionListener {
    private CostAnalysisFrame parent;
    private Map resourceMap;
    private CostAnalyzer analyzer;

    public AddMaterialListener(CostAnalysisFrame costAnalysis, Map resourceMap, CostAnalyzer analyzer) {
        this.parent = costAnalysis;
        this.resourceMap = resourceMap;
        this.analyzer = analyzer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MaterialPane pane = new MaterialPane();
        pane.setMaterialTypeChangedAction(new MaterialTypeChangedListener(pane, resourceMap));
        pane.setUpdatePriceAction(new UpdatePriceListener(pane, analyzer));
        parent.addMaterialPane(pane);
    }
}
