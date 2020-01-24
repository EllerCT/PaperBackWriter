package controllers;

import swing_frames.DesktopFrame;
import swing_frames.MainMenuFrame;

import javax.swing.*;

public class DesktopController {
    private DesktopFrame desktopFrame;

    public DesktopController() {
        this.desktopFrame = new DesktopFrame();
    }

    public static void main(String[] args) {
        DesktopController dc = new DesktopController();
        dc.open();
        dc.show(new MainMenuFrame().getContentPane(), "PBW");
    }

    public void open() {
        JFrame frame = new JFrame("Paper Back Writer");
        frame.setContentPane(desktopFrame.getContentPane());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void show(JPanel content, String title) {
        JInternalFrame internalFrame = new JInternalFrame(title, true, true, true, true);
        internalFrame.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        internalFrame.setContentPane(content);
        desktopFrame.showInnerWindow(internalFrame);
        internalFrame.pack();
        internalFrame.setVisible(true);
    }
}
