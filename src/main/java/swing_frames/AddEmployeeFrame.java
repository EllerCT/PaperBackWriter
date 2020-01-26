package swing_frames;

import javax.swing.*;
import java.awt.event.ActionListener;

public class AddEmployeeFrame extends JFrame {
    private JButton closeButton;
    private JButton confirmButton;
    private JTextField newEmployeePinField;
    private JTextField newEmployeeNameField;
    private JTextField newEmployeeHoursField;
    private JTextField newEmployeePointsField;
    private JPanel contentPane;

    public void setCloseAction(ActionListener action){
        closeButton.addActionListener(action);
    }

    public void setConfirmAction(ActionListener action){
        confirmButton.addActionListener(action);
    }

    public String getNewEmployeePin(){
        return newEmployeePinField.getText();
    }

    public String getNewEmployeeName(){
        return newEmployeeNameField.getText();
    }

    public String getNewEmployeeHours(){
        return newEmployeeHoursField.getText();
    }

    public String getNewEmployeePoints(){
        return newEmployeePointsField.getText();
    }

    public void clearFields(){
        newEmployeePinField.setText("");
        newEmployeeHoursField.setText("0");
        newEmployeeNameField.setText("");
        newEmployeePointsField.setText("0");
    }

    public JPanel getContentPane() {
        return contentPane;
    }
}
