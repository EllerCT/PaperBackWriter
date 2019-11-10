import java.time.Duration;
import java.time.LocalDateTime;

public class TimeClock {

    public void clockIn(Employee employee){
        employee.setClockInTime(LocalDateTime.now());
    }

    public void clockOut(Employee employee){
        Duration hoursWorked = Duration.between(employee.getClockInTime(),LocalDateTime.now());
        employee.setHours(hoursWorked);
        employee.setClockInTime(null);
    }
}
