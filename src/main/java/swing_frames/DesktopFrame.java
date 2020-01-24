package swing_frames;

import javax.swing.*;

public class DesktopFrame {
    private JPanel contentPane;
    private JDesktopPane desktopPane;

    public DesktopFrame() {
        desktopPane.setLayout(null);
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public void showInnerWindow(JInternalFrame innerFrame) {
        desktopPane.add(innerFrame);
    }

}
