import javax.swing.*;
import java.awt.event.ActionListener;

public class TimeClockFrame {
    private JTextField pinField;
    private JButton clockInOutButton;
    private JLabel pinLabel;
    private JPanel contentPane;

    public void setClockInOutAction(ActionListener action){
        clockInOutButton.addActionListener(action);
    }

    public String getPin(){
        return pinField.getText();
    }

    public void clearPin(){
        pinField.setText("");
    }

    public JPanel getPanel() {
        return contentPane;
    }
}
