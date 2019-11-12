package data_structures;

public class Event {
    private String eventName;
    private String eventDescription;
    private String eventCode;
    private int pointWorth;

    public Event(String code, int points){
        this.eventCode = code;
        this.pointWorth = points;
        this.eventName=code;
        this.eventDescription ="";
    }

    public String getEventCode() {
        return eventCode;
    }

    public int getPointWorth() {
        return pointWorth;
    }

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
}
