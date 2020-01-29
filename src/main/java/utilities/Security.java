package utilities;

import javax.swing.*;

//TODO: Should this be here?
public class Security {

    public boolean getAuthorization() {
        JPasswordField passwordField = new JPasswordField();
        JOptionPane.showConfirmDialog(null, passwordField, "Enter password to confirm:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        return String.valueOf(passwordField.getPassword()).hashCode() == 552231974;
    }
}
