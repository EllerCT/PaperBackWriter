package data_structures;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Employee {
    private String name;
    private Duration totalHours;
    private Duration weeklyHours;
    private int points;
    private PinNumber pin;
    private LocalDateTime lastClockInTime;
    private LocalDateTime lastClockOutTime;

    public static String generateKeyFor(Employee employee) {
        return employee.getPin().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Duration getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Duration totalHours) {
        this.totalHours = totalHours;
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

    public void setLastClockInTime(LocalDateTime time){
        this.lastClockInTime = time;
    }

    public LocalDateTime getLastClockInTime() {
        return lastClockInTime;
    }

    public LocalDateTime getLastClockOutTime() {
        return lastClockOutTime;
    }

    public void setLastClockOutTime(LocalDateTime lastClockOutTime) {
        this.lastClockOutTime = lastClockOutTime;
    }

    public Duration getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(Duration weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return name.equals(employee.name) &&
                pin.equals(employee.pin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, pin);
    }
}
