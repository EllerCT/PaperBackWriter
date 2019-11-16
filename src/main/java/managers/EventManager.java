package managers;

import data_structures.Event;
import io_pipes.EmployeeIOPipe;
import io_pipes.EventIOPipe;
import java.util.HashMap;
import java.util.Map;

public class EventManager
{
    private EventIOPipe eventIOPipe;
    private Map<String, Event> eventMap;

    public EventManager() { eventMap = new HashMap<String, Event>(); }

    public void fetchEvent(){
        eventMap.clear();
        eventMap.putAll(eventIOPipe.loadEvents());
    }

    public void storeEvents(){
        eventIOPipe.saveEvents(eventMap);
    }

    public void setEventIOPipe( EventIOPipe eventIOPipe) { this.eventIOPipe = eventIOPipe; }

    public Map<String, Event> getEventMap(){
        return eventMap;
    }

    public void newEvent(Event event){
        eventMap.put(event.getEventName(), event);
    }

    public void removeEvent(Event event){
        eventMap.remove(event.getEventName());
    }

    public void updateEvent(Event event){
        if (eventMap.containsKey(event.getEventName())) {
            eventMap.put(event.getEventName(), event);
        }
    }

    public Event getEvent(EventName name) {
        if (this.eventMap.containsKey(name)){
            Event mapEvent = this.eventMap.get(name);
            Event safeEvent = new Event();
            safeEvent.setEventDescription(mapEvent.getEventDescription());
            safeEvent.setEventName(mapEvent.getEventName());
            safeEvent.setEventCode(mapEvent.getEventCode());
            safeEvent.setPointWorth(mapEvent.getPointWorth());
            return this.eventMap.get(name);
        }
        return null;
    }

    public void setEventMap(HashMap<String, Event > newMap) {
        this.eventMap = newMap;
    }

}
