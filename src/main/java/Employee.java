import java.time.Duration;
import java.time.LocalDateTime;

public class Employee {
    private String name;
    private Duration hours;
    private int points;
    private PinNumber pin;
    private LocalDateTime clockInTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Duration getHours() {
        return hours;
    }

    public void setHours(Duration hours) {
        this.hours = hours;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public PinNumber getPin() {
        return pin;
    }

    public void setPin(PinNumber pin) {
        this.pin = pin;
    }

    public void setClockInTime(LocalDateTime time){
        this.clockInTime = time;
    }

    public LocalDateTime getClockInTime() {
        return clockInTime;
    }
}
