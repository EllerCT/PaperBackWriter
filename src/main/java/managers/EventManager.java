package managers;

import data_structures.Event;
import io_pipes.EventIOPipe;

import java.util.HashMap;
import java.util.Map;

public class EventManager
{
    private EventIOPipe eventIOPipe;
    private Map<String, Event> eventMap;

    public EventManager() { eventMap = new HashMap<String, Event>(); }

    public void fetchEvents() {
        eventMap.putAll(eventIOPipe.load());
    }

    public void storeEvents(){
        eventIOPipe.save(eventMap);
    }

    public void setEventIOPipe( EventIOPipe eventIOPipe) { this.eventIOPipe = eventIOPipe; }

    public Map<String, Event> getEventMap(){
        return eventMap;
    }

    public void newEvent(Event event){
        eventMap.put(event.getEventCode(), event);
    }

    public void removeEvent(Event event){
        eventMap.remove(event.getEventCode());
    }

    public void updateEvent(Event event){
        if (eventMap.containsKey(event.getEventName())) {
            eventMap.put(event.getEventName(), event);
        }
    }

    public Event getEvent(String code) {
        if (this.eventMap.containsKey(code)) {
            Event mapEvent = this.eventMap.get(code);
            Event safeEvent = new Event(mapEvent.getEventCode(), mapEvent.getPointWorth());
            safeEvent.setEventDescription(mapEvent.getEventDescription());
            safeEvent.setEventName(mapEvent.getEventName());
            safeEvent.setEventConfirmationCode(mapEvent.getEventConfirmationCode());
            return this.eventMap.get(code);
        }
        return null;
    }

    public void setEventMap(HashMap<String, Event > newMap) {
        this.eventMap = newMap;
    }

}
