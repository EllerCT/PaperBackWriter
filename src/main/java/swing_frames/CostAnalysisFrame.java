package swing_frames;

import data_structures.Resource;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public class CostAnalysisFrame extends JFrame {
    private JPanel contentPane;
    private JTextField totalCost;
    private JTextField name;
    private JTextField date;
    private JTextField productType;
    private JComboBox paperType;
    private JComboBox threadType;
    private JComboBox glueType;
    private JComboBox boardType;
    private JComboBox decoratedPaperType;
    private JComboBox spineType;
    private JComboBox endBandType;
    private JTextArea otherMaterial;
    private JTextField otherCost;
    private JButton cancelButton;
    private JButton submitButton;
    private JTextField idNumber;
    private JTextField grade;
    private JTextArea productDescription;
    private JTextField paperCost;
    private JTextField threadCost;
    private JTextField glueCost;
    private JTextField boardCost;
    private JTextField decoratedPaperCost;
    private JTextField spineCost;
    private JTextField endBandCost;
    private JTextField paperUnits;
    private JTextField threadUnits;
    private JTextField glueUnits;
    private JTextField boardUnits;
    private JTextField decoratedPaperUnits;
    private JTextField spineUnits;
    private JTextField endBandUnits;
    private JTextField otherUnits;
    private JButton calculateButton;
    private JTextField spiritsUnits;
    private JTextField spiritsCost;
    private JComboBox spiritsType;

    public JPanel getContentPane() {
        return this.contentPane;
    }

    // Unit fields

    public int getSpiritsUnits() {
        String text = spiritsUnits.getText();
        if (text.isEmpty()) {
            return 0;
        } else if (text.matches("[0-9]*")) {
            return Integer.parseInt(text);
        }
        return 0;
    }

    public void setSpiritUnits(int units) {
        spiritsUnits.setText(Integer.toString(units));
    }

    public String getSpiritsCost() {
        return spiritsCost.getText();
    }

    public void setSpiritsCost(String text) {
        spiritsCost.setText(text);
    }

    public int getPaperUnits() {
        String text = paperUnits.getText();
        if (text.isEmpty()) {
            return 0;
        } else if (text.matches("[0-9]*")) {
            return Integer.parseInt(text);
        }
        return 0;
    }

    public void setPaperUnits(int units) {
        paperUnits.setText(Integer.toString(units));
    }

    public int getThreadUnits() {
        String text = threadUnits.getText();
        if (text.isEmpty()) {
            return 0;
        } else if (text.matches("[0-9]*")) {
            return Integer.parseInt(text);
        }
        return 0;
    }

    public void setThreadUnits(int units) {
        threadUnits.setText(Integer.toString(units));
    }

    public int getGlueUnits() {
        String text = glueUnits.getText();
        if (text.isEmpty()) {
            return 0;
        } else if (text.matches("[0-9]*")) {
            return Integer.parseInt(text);
        }
        return 0;
    }

    public void setGlueUnits(int units) {
        glueUnits.setText(Integer.toString(units));
    }

    public int getBoardUnits() {
        String text = boardUnits.getText();
        if (text.isEmpty()) {
            return 0;
        } else if (text.matches("[0-9]*")) {
            return Integer.parseInt(text);
        }
        return 0;
    }

    public void setBoardUnits(int units) {
        boardUnits.setText(Integer.toString(units));
    }

    public int getDecoratedPaperUnits() {
        String text = decoratedPaperUnits.getText();
        if (text.isEmpty()) {
            return 0;
        } else if (text.matches("[0-9]*")) {
            return Integer.parseInt(text);
        }
        return 0;
    }

    public void setDecoratedPaperUnits(int units) {
        decoratedPaperUnits.setText(Integer.toString(units));
    }

    public int getSpineUnits() {
        String text = spineUnits.getText();
        if (text.isEmpty()) {
            return 0;
        } else if (text.matches("[0-9]*")) {
            return Integer.parseInt(text);
        }
        return 0;
    }

    public void setSpineUnits(int units) {
        spineUnits.setText(Integer.toString(units));
    }

    public int getEndBandUnits() {
        String text = endBandUnits.getText();
        if (text.isEmpty()) {
            return 0;
        } else if (text.matches("[0-9]*")) {
            return Integer.parseInt(text);
        }
        return 0;
    }

    public void setEndBandUnits(int units) {
        endBandUnits.setText(Integer.toString(units));
    }

    public int getOtherUnits() {
        String text = otherUnits.getText();
        if (text.isEmpty()) {
            return 0;
        } else if (text.matches("[0-9]*")) {
            return Integer.parseInt(text);
        }
        return 0;
    }

    public void setOtherUnits(int units) {
        otherUnits.setText(Integer.toString(units));
    }

    // Combo Boxes

    public void setPaperTypeOptions(List<Resource> resources) {
        for (Resource resource : resources) {
            paperType.addItem(resource);
        }
        if (paperType.getItemCount() > 0) paperType.setSelectedIndex(0);
    }

    public Resource getCurrentPaperType() {
        return (Resource) paperType.getSelectedItem();
    }

    public void setThreadTypeOptions(List<Resource> resources) {
        for (Resource resource : resources) {
            threadType.addItem(resource);
        }
        if (threadType.getItemCount() > 0) threadType.setSelectedIndex(0);
    }

    public Resource getCurrentThreadType() {
        return (Resource) threadType.getSelectedItem();
    }

    public void setGlueTypeOptions(List<Resource> resources) {
        for (Resource resource : resources) {
            glueType.addItem(resource);
        }
        if (glueType.getItemCount() > 0) glueType.setSelectedIndex(0);
    }

    public Resource getCurrentGlueType() {
        return (Resource) glueType.getSelectedItem();
    }

    public void setBoardTypeOptions(List<Resource> resources) {
        for (Resource resource : resources) {
            boardType.addItem(resource);
        }
        if (boardType.getItemCount() > 0) boardType.setSelectedIndex(0);
    }

    public Resource getCurrentBoardType() {
        return (Resource) boardType.getSelectedItem();
    }

    public void setDecoratedPaperTypeOptions(List<Resource> resources) {
        for (Resource resource : resources) {
            decoratedPaperType.addItem(resource);
        }
        if (decoratedPaperType.getItemCount() > 0) decoratedPaperType.setSelectedIndex(0);
    }

    public Resource getCurrentDecoratedPaperType() {
        return (Resource) decoratedPaperType.getSelectedItem();

    }

    public void setSpineTypeOptions(List<Resource> resources) {
        for (Resource resource : resources) {
            spineType.addItem(resource);
        }
        if (spineType.getItemCount() > 0) spineType.setSelectedIndex(0);
    }

    public Resource getCurrentSpineType() {
        return (Resource) spineType.getSelectedItem();
    }

    public void setEndBandTypeOptions(List<Resource> resources) {
        for (Resource resource : resources) {
            endBandType.addItem(resource);
        }
        if (endBandType.getItemCount() > 0) endBandType.setSelectedIndex(0);
    }

    public Resource getCurrentEndBandType() {
        return (Resource) endBandType.getSelectedItem();
    }

    public void setSpiritsTypeOptions(List<Resource> resources) {
        for (Resource resource : resources) {
            spiritsType.addItem(resource);
        }
        if (spiritsType.getItemCount() > 0) endBandType.setSelectedIndex(0);
    }

    public Resource getCurrentSpiritsType() {
        return (Resource) spiritsType.getSelectedItem();
    }

    // Text fields

    public String getEndBandCost() {
        return endBandCost.getText();
    }

    public void setEndBandCost(String text) {
        endBandCost.setText(text);
    }

    public String getSpineCost() {
        return spineCost.getText();
    }

    public void setSpineCost(String text) {
        spineCost.setText(text);
    }

    public String getDecoratedPaperCost() {
        return decoratedPaperCost.getText();
    }

    public void setDecoratedPaperCost(String text) {
        decoratedPaperCost.setText(text);
    }

    public String getBoardCost() {
        return boardCost.getText();
    }

    public void setBoardCost(String text) {
        boardCost.setText(text);
    }

    public String getGlueCost() {
        return glueCost.getText();
    }

    public void setGlueCost(String text) {
        glueCost.setText(text);
    }

    public String getThreadCost() {
        return threadCost.getText();
    }

    public void setThreadCost(String text) {
        threadCost.setText(text);
    }

    public String getPaperCost() {
        return paperCost.getText();
    }

    public void setPaperCost(String text) {
        paperCost.setText(text);
    }

    public String getProductDescription() {
        return productDescription.getText();
    }

    public void setProductDescription(String text) {
        productDescription.setText(text);
    }

    public String getGrade() {
        return grade.getText();
    }

    public void setGrade(String text) {
        grade.setText(text);
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

    public void setDate(String text) {
        date.setText(text);
    }

    public String getProductType() {
        return productType.getText();
    }

    public void setProductType(String text) {
        productType.setText(text);
    }

    public String getOtherMaterial() {
        return otherMaterial.getText();
    }

    public void setOtherMaterial(String text) {
        otherMaterial.setText(text);
    }

    public String getOtherCost() {
        return otherCost.getText();
    }

    public void setOtherCost(String text) {
        otherCost.setText(text);
    }

    // Buttons

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
