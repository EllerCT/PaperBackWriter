import javax.swing.*;

public class UserInterfaceController {
    public static void show(JPanel contentPanel, int closeBehavior){
        JFrame frame = new JFrame();
        frame.setContentPane(contentPanel);
        frame.setDefaultCloseOperation(closeBehavior);
        frame.pack();
        frame.setVisible(true);
    }
}
