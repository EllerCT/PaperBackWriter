import utilities.Initializer;
import utilities.Updater;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        // Temporary, for I4 only.
        for (String arg : args) {
            if (arg.contains("update")) {
                new Updater().updateProductFile();
            }
        }
        SwingUtilities.invokeLater(new Initializer());
    }
}
