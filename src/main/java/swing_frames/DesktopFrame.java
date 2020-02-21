package swing_frames;

import javax.swing.*;
import java.awt.event.ActionListener;

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

    public void setClockInOutButtonAction(ActionListener listener) {
        this.clockInOutButton.addActionListener(listener);
    }

    public void setAttendanceButtonAction(ActionListener listener) {
        this.attendanceButton.addActionListener(listener);
    }

    public void setEmployeesButtonAction(ActionListener listener) {
        this.employeesButton.addActionListener(listener);
    }

    public void setEventsButtonAction(ActionListener listener) {
        this.eventsButton.addActionListener(listener);
    }

    public void setCostAnalysisButtonAction(ActionListener listener) {
        this.costAnalysisButton.addActionListener(listener);
    }

    public void setProductBrowserButtonAction(ActionListener listener) {
        this.productBrowserButton.addActionListener(listener);
    }

    public void setResourcesButtonAction(ActionListener listener) {
        this.resourcesButton.addActionListener(listener);
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public void showInnerWindow(JInternalFrame innerFrame) {
        desktopPane.add(innerFrame);
    }

}
