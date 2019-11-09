import javax.swing.*;

public class UserInterfaceController implements TimeclockFrontend{
    private void show(JPanel contentPanel, int closeBehavior){
        JFrame frame = new JFrame();
        frame.setContentPane(contentPanel);
        frame.setDefaultCloseOperation(closeBehavior);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void mainMenu() {

    }

    @Override
    public void timeClock() {

    }

    @Override
    public void addEmployee() {

    }

    @Override
    public void attendEvent() {

    }

    @Override
    public void viewEmployees() {

    }

    @Override
    public void manageEvents() {

    }

    @Override
    public void getConfirmation() {

    }
}
