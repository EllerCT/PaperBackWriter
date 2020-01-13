package swing_frames;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class ProductBrowserFrame {
    private JPanel contentPanel;
    private JButton viewButton;
    private JButton closeButton;
    private JTable productsTable;
    private JCheckBox grading;

    public boolean isGrading() {
        return grading.isSelected();
    }

    public void setGradingListener(ItemListener listener) {
        grading.addItemListener(listener);
    }

    public void setViewButtonListener(ActionListener listener) {
        this.viewButton.addActionListener(listener);
    }

    public void setCloseButtonListener(ActionListener listener) {
        this.closeButton.addActionListener(listener);
    }

    public JPanel getContentPanel() {
        return this.contentPanel;
    }

    public TableModel getProductsTableModel() {
        return this.productsTable.getModel();
    }

    public void setProductsTableModel(TableModel model) {
        productsTable.setModel(model);
    }
}
