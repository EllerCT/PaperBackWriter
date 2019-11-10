public class Event {
    private String eventCode;
    private int pointWorth;

    public Event(String code, int points){
        this.eventCode = code;
        this.pointWorth = points;
    }

    public String getEventCode() {
        return eventCode;
    }

    public int getPointWorth() {
        return pointWorth;
    }
}
