import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public class ClockPointsFrame {
    private JPanel contentPane;
    private JPanel entryPanel;
    private JPanel buttonAreaPanel;
    private JButton confirmButton;
    private JTextField pinField;
    private JComboBox<String> eventCombo;

    public void setConfirmAction(ActionListener action){
        confirmButton.addActionListener(action);
    }

    public String getPin(){
        return pinField.getText();
    }

    public void clearPin(){
        pinField.setText("");
    }

    public void addEventBoxItem(String item){
        eventCombo.addItem(item);
    }

    public void populateEventBox(List<String> items){
        for (String item : items){
            eventCombo.addItem(item);
        }
    }

    public String getSelectedEvent(){
        return (String) eventCombo.getSelectedItem();
    }

    public JPanel getPanel() {
        return contentPane;
    }
}
