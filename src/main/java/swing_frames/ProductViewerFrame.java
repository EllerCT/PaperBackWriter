package swing_frames;

import ui_components.ReadOnlyMaterialPane;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ProductViewerFrame extends JInternalFrame {
    private JPanel contentPane;
    private JTextField totalCost;
    private JTextField name;
    private JTextField date;
    private JTextField productType;
    private JButton okButton;
    private JTextField idNumber;
    private JTextField grade;
    private JTextArea productDescription;
    private JScrollPane scrollPane;
    private Box materialsBox;


    public JPanel getContentPane() {
        return this.contentPane;
    }

    public void createUIComponents() {
        materialsBox = Box.createVerticalBox();
    }

    public void addReadOnlyMaterialPane(ReadOnlyMaterialPane readOnlyMaterialPane) {
        materialsBox.add(readOnlyMaterialPane);
        materialsBox.revalidate();
        materialsBox.repaint();
    }

    public void setOkButtonListener(ActionListener listener) {
        this.okButton.addActionListener(listener);
    }


    public void setTotalCost(String totalCost) {
        this.totalCost.setText(totalCost);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setDate(String date) {
        this.date.setText(date);
    }

    public void setProductType(String productType) {
        this.productType.setText(productType);
    }

    public void setIdNumber(String idNumber) {
        this.idNumber.setText(idNumber);
    }

    public void setGrade(String grade) {
        this.grade.setText(grade);
    }

    public void setProductDescription(String productDescription) {
        this.productDescription.setText(productDescription);
    }
    public String getGrade() {
        return this.grade.getText();
    }
}