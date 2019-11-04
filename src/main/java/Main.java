import javax.swing.*;

public class Main {
    public static void main(String[] args){
        UserInterfaceController.show(new MainMenuFrame().getPanel(), JFrame.EXIT_ON_CLOSE)   ;
    }
}
