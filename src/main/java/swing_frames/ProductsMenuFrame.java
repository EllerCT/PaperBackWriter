package swing_frames;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ProductsMenuFrame extends JFrame {
    private JPanel contentPane;
    private JButton costAnalysisButton;
    private JButton resourcesButton;
    private JButton browseProductsButton;

    public JPanel getPane() {
        return contentPane;
    }

    public void setCostAnalysisButtonListener(ActionListener action) {
        costAnalysisButton.addActionListener(action);
    }

    public void setResourcesButtonListener(ActionListener action) {
        resourcesButton.addActionListener(action);
    }

    public void setBrowseProductsButtonListener(ActionListener action) {
        browseProductsButton.addActionListener(action);
    }
}
