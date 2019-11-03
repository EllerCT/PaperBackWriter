import javax.swing.*;

public class UserInterfaceController {
    public void showMenu(){
        JFrame frame = new JFrame("MainMenuFrame");
        frame.setContentPane(new MainMenuFrame().getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
