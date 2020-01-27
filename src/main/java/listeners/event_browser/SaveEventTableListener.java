package listeners.event_browser;

import data_structures.Event;
import managers.EventManager;
import swing_frames.ManageEventsFrame;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

public class SaveEventTableListener implements ActionListener {


    private ManageEventsFrame manageEventsFrame;
    private EventManager eventManager;

    public SaveEventTableListener(ManageEventsFrame manageEventsFrame, EventManager eventManager) {
        this.manageEventsFrame = manageEventsFrame;
        this.eventManager = eventManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultTableModel tableModel = (DefaultTableModel) manageEventsFrame.getModel();
        Vector<Vector> rows = tableModel.getDataVector();
        HashMap<String, Event> newEventMap = new HashMap<>();
        for (Vector<String> row : rows) {
            String code = row.get(0);
            String name = row.get(1);
            int worth = Integer.parseInt(row.get(2));
            String desc = row.get(3);
            String confirmation = row.get(4);
            Event event = new Event(code, worth);
            event.setEventName(name);
            event.setEventDescription(desc);
            event.setEventConfirmationCode(confirmation);
            newEventMap.put(code, event);
        }
        eventManager.setEventMap(newEventMap);
        eventManager.storeEvents();
        manageEventsFrame.dispose();
    }
}
