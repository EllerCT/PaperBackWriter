package swing_frames;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MainMenuFrame extends JFrame {
    private JButton employeeButton;
    private JButton productButton;
    private JPanel contentPane;

    public void setEmployeeButtonAction(ActionListener action) {
        employeeButton.addActionListener(action);
    }

    public void setProductButtonAction(ActionListener action) {
        productButton.addActionListener(action);
    }

    public JPanel getContentPane() {
        return contentPane;
    }
}
