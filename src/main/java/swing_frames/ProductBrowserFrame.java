package swing_frames;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;

public class ProductBrowserFrame extends JFrame {
    private JPanel contentPanel;
    private JButton viewButton;
    private JButton closeButton;
    private JTable productsTable;
    private JCheckBox grading;
    private JButton enableGradingButton;

    public boolean isGrading() {
        return grading.isSelected();
    }

    public void setEnableGradingButtonListener(ActionListener listener) {
        this.enableGradingButton.addActionListener(listener);
    }

    public void enableGradingCheckbox() {
        this.grading.setEnabled(true);
    }

    public JTable getProductsTable() {
        return productsTable;
    }


    public void setViewButtonListener(ActionListener listener) {
        this.viewButton.addActionListener(listener);
    }

    public void setCloseButtonListener(ActionListener listener) {
        this.closeButton.addActionListener(listener);
    }

    public JPanel getContentPane() {
        return this.contentPanel;
    }

    public TableModel getProductsTableModel() {
        return this.productsTable.getModel();
    }

    public void setProductsTableModel(TableModel model) {
        productsTable.setModel(model);
    }
}
