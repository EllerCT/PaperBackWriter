package data_structures;

import java.util.Objects;

public class Event {
    private String eventName;
    private String eventDescription;
    private String eventCode;
    private int pointWorth;
    private String eventConfirmationCode;

    public Event(String code, int points){
        this.eventCode = code;
        this.pointWorth = points;
        this.eventName = "";
        this.eventDescription ="";
        this.eventConfirmationCode = "";
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode){this.eventCode = eventCode;}

    public int getPointWorth() {
        return pointWorth;
    }

    public void setPointWorth(int pointWorth){this.pointWorth = pointWorth;}

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventConfirmationCode() {
        return eventConfirmationCode;
    }

    public void setEventConfirmationCode(String eventConfirmationCode) {
        this.eventConfirmationCode = eventConfirmationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(eventName, event.eventName) &&
                eventCode.equals(event.eventCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventName, eventCode);
    }
}
