package listeners.cost_analysis;

import swing_frames.CostAnalysisFrame;
import ui_components.MaterialPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddMaterialListener implements ActionListener {
    private CostAnalysisFrame parent;

    public AddMaterialListener(CostAnalysisFrame costAnalysis) {
        this.parent = costAnalysis;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        parent.addMaterialPane(new MaterialPane());
    }
}
