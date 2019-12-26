package managers;

import data_structures.Employee;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

public class TimeClockManager {

    public static boolean isNewWeekFor(Employee employee){
        ZonedDateTime lastClockOut = employee.getLastClockOutTime()
                .atZone(ZoneId.systemDefault());
        int currentWeekOfYear = new GregorianCalendar()
                .get(GregorianCalendar.WEEK_OF_YEAR);
        int lastClockOutWeekOfYear = GregorianCalendar.from(lastClockOut)
                .get(GregorianCalendar.WEEK_OF_YEAR);
        return lastClockOutWeekOfYear != currentWeekOfYear;
    }

    public void clockIn(Employee employee){
        if (isNewWeekFor(employee)){
            employee.setWeeklyHours(Duration.ZERO);
            employee.setLastClockInTime(LocalDateTime.now());
        } else {
            employee.setLastClockInTime(LocalDateTime.now());
        }
    }

    public void clockOut(Employee employee){
        Duration hoursWorked = Duration.between(employee.getLastClockInTime(),LocalDateTime.now());
        employee.setTotalHours(employee.getTotalHours().plus(hoursWorked));
        employee.setWeeklyHours(employee.getWeeklyHours().plus(hoursWorked));
        employee.setLastClockOutTime(LocalDateTime.now());
    }

    public boolean checkIfClockedIn(Employee employee) {
        return employee.getLastClockInTime().isAfter(
                employee.getLastClockOutTime());
    }
}
