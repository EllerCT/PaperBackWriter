package managers;

import data_structures.Event;

public class EventManager extends AbstractManager {
    public void add(Object object) {
        if (object instanceof Event) {
            String key = Event.generateKeyFor((Event) object);
            super.map.put(key, object);
        }
    }

    public void remove(Object object) {
        if (object instanceof Event) {
            Event event = (Event) object;
            String key = Event.generateKeyFor(event);
            super.map.remove(key);
        }
    }
}
