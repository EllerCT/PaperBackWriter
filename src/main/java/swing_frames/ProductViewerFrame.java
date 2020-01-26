package swing_frames;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ProductViewerFrame extends JFrame {
    private JPanel contentPane;
    private JTextField totalCost;
    private JTextField name;
    private JTextField date;
    private JTextField productType;
    private JTextField paperType;
    private JTextField threadType;
    private JTextField glueType;
    private JTextField boardType;
    private JTextField decoratedPaperType;
    private JTextField spineType;
    private JTextField endBandType;
    private JTextArea otherMaterial;
    private JTextField otherCost;
    private JButton okButton;
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
    private JTextField spiritsUnits;
    private JTextField spiritsCost;
    private JTextField spiritsType;

    public JPanel getContentPane() {
        return this.contentPane;
    }

    public void setOkButtonListener(ActionListener listener) {
        this.okButton.addActionListener(listener);
    }

    public void setSpiritsType(String text) {
        spiritsType.setText(text);
    }

    public void setSpiritUnits(String units) {
        spiritsUnits.setText(units);
    }

    public void setSpiritsCost(String text) {
        spiritsCost.setText(text);
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

    public void setPaperType(String paperType) {
        this.paperType.setText(paperType);
    }

    public void setThreadType(String threadType) {
        this.threadType.setText(threadType);
    }

    public void setGlueType(String glueType) {
        this.glueType.setText(glueType);
    }

    public void setBoardType(String boardType) {
        this.boardType.setText(boardType);
    }

    public void setDecoratedPaperType(String decoratedPaperType) {
        this.decoratedPaperType.setText(decoratedPaperType);
    }

    public void setSpineType(String spineType) {
        this.spineType.setText(spineType);
    }

    public void setEndBandType(String endBandType) {
        this.endBandType.setText(endBandType);
    }

    public void setOtherMaterial(String otherMaterial) {
        this.otherMaterial.setText(otherMaterial);
    }

    public void setOtherCost(String otherCost) {
        this.otherCost.setText(otherCost);
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

    public void setPaperCost(String paperCost) {
        this.paperCost.setText(paperCost);
    }

    public void setThreadCost(String threadCost) {
        this.threadCost.setText(threadCost);
    }

    public void setGlueCost(String glueCost) {
        this.glueCost.setText(glueCost);
    }

    public void setBoardCost(String boardCost) {
        this.boardCost.setText(boardCost);
    }

    public void setDecoratedPaperCost(String decoratedPaperCost) {
        this.decoratedPaperCost.setText(decoratedPaperCost);
    }

    public void setSpineCost(String spineCost) {
        this.spineCost.setText(spineCost);
    }

    public void setEndBandCost(String endBandCost) {
        this.endBandCost.setText(endBandCost);
    }

    public void setPaperUnits(String paperUnits) {
        this.paperUnits.setText(paperUnits);
    }

    public void setThreadUnits(String threadUnits) {
        this.threadUnits.setText(threadUnits);
    }

    public void setGlueUnits(String glueUnits) {
        this.glueUnits.setText(glueUnits);
    }

    public void setBoardUnits(String boardUnits) {
        this.boardUnits.setText(boardUnits);
    }

    public void setDecoratedPaperUnits(String decoratedPaperUnits) {
        this.decoratedPaperUnits.setText(decoratedPaperUnits);
    }

    public void setSpineUnits(String spineUnits) {
        this.spineUnits.setText(spineUnits);
    }

    public void setEndBandUnits(String endBandUnits) {
        this.endBandUnits.setText(endBandUnits);
    }

    public void setOtherUnits(String otherUnits) {
        this.otherUnits.setText(otherUnits);
    }

    public String getGrade() {
        return this.grade.getText();
    }
}