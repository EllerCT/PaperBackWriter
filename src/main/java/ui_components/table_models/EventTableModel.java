package ui_components.table_models;

import data_structures.Event;

import java.util.Map;
import java.util.Vector;

public class EventTableModel extends ClassAwareTableModel {

    public EventTableModel(Map eventMap) {
        addColumn("Code");
        addColumn("Name");
        addColumn("Worth");
        addColumn("Description");
        addColumn("Confirmation Code");

        for (Object value : eventMap.values()) {
            Event event = (Event) value;
            String code = event.getEventCode();
            int worth = event.getPointWorth();
            String name = event.getEventName();
            String desc = event.getEventDescription();
            String confirm = event.getEventConfirmationCode();
            Vector<String> newRow = new Vector<>();
            newRow.add(code);
            newRow.add(name);
            newRow.add(String.valueOf(worth));
            newRow.add(desc);
            newRow.add(confirm);
            addRow(newRow);
        }
    }
}
