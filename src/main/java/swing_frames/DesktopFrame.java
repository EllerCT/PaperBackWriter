package swing_frames;

import javax.swing.*;

public class DesktopFrame extends JFrame {
    private JPanel contentPane;
    private JDesktopPane desktopPane;
    private JTabbedPane tabbedPane1;
    private JToolBar employeeToolbar;
    private JToolBar productToolbar;
    private JButton clockInOutButton;
    private JButton attendanceButton;
    private JButton employeesButton;
    private JButton eventsButton;
    private JButton costAnalysisButton;
    private JButton productBrowserButton;
    private JButton resourcesButton;

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
