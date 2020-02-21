package swing_frames;

import data_structures.Material;
import ui_components.MaterialPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CostAnalysisFrame extends JInternalFrame {
    private JPanel contentPane;
    private JTextField totalCost;
    private JTextField name;
    private JTextField date;
    private JTextField productType;
    private JButton cancelButton;
    private JButton submitButton;
    private JTextField idNumber;
    private JTextField grade;
    private JTextArea productDescription;
    private JButton calculateButton;
    private JScrollPane scrollPane;
    private JButton addMaterialButton;
    private Box materialsBox;

    public void createUIComponents() {
        materialsBox = Box.createVerticalBox();
    }

    public JPanel getContentPane() {
        return this.contentPane;
    }

    public void addMaterialPane(MaterialPane materialPane) {
        materialsBox.add(materialPane);
        materialsBox.revalidate();
        materialsBox.repaint();
    }

    public List<Material> getMaterials() {
        ArrayList<Material> materials = new ArrayList<>();
        for (Component component : materialsBox.getComponents()) {
            if (component instanceof MaterialPane) {
                Material fromPane = ((MaterialPane) component).getMaterial();
                if (fromPane != null) {
                    materials.add(fromPane);
                }
            }
        }
        return materials;
    }

    public ArrayList<Double> getMaterialCosts() {
        ArrayList<Double> costs = new ArrayList<>();
        for (Component component : materialsBox.getComponents()) {
            if (component instanceof MaterialPane) {
                costs.add(((MaterialPane) component).getPrice());
            }
        }
        return costs;
    }

    public String getProductDescription() {
        return productDescription.getText();
    }

    public String getGrade() {
        return grade.getText();
    }

    public String getIdNumber() {
        return idNumber.getText();
    }

    public void setIdNumber(String text) {
        idNumber.setText(text);
    }

    public String getTotalCost() {
        return totalCost.getText();
    }

    public void setTotalCost(double cost) {
        String total = String.format("%.2f", cost);
        totalCost.setText(total);
    }

    public String getName() {
        return name.getText();
    }

    public void setName(String text) {
        name.setText(text);
    }

    public String getDate() {
        return date.getText();
    }

    public String getProductType() {
        return productType.getText();
    }

    public void setAddMaterialButtonAction(ActionListener action) {
        addMaterialButton.addActionListener(action);
    }

    public void setCancelButtonAction(ActionListener action) {
        cancelButton.addActionListener(action);
    }

    public void setSubmitButtonAction(ActionListener action) {
        submitButton.addActionListener(action);
    }

    public void setCalculateButtonAction(ActionListener action) {
        calculateButton.addActionListener(action);
    }


}
