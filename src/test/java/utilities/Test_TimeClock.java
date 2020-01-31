package utilities;

import data_structures.Employee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Test_TimeClock {

    private Employee testingEmployee;
    private TimeClock tc;

    @Before
    public void setUp() {
        testingEmployee = new Employee();
        testingEmployee.setTotalHours(Duration.ZERO);
        testingEmployee.setWeeklyHours(Duration.ZERO);
        testingEmployee.setLastClockInTime(LocalDateTime.now());
        testingEmployee.setLastClockOutTime(LocalDateTime.now());

        tc = new TimeClock();
    }

    @Test
    public void isNewWeekFor_IsNewWeek_True() {
        testingEmployee.setLastClockOutTime(LocalDateTime.of(2020, 1, 1, 0, 0));
        boolean actual = TimeClock.isNewWeekFor(testingEmployee);
        Assert.assertTrue(actual);
    }

    @Test
    public void isNewWeekFor_NotNewWeek_False() {
        testingEmployee.setLastClockOutTime(LocalDateTime.now());
        boolean actual = TimeClock.isNewWeekFor(testingEmployee);
        Assert.assertFalse(actual);
    }

    @Test
    public void isClockedIn_ClockedIn_True() {
        testingEmployee.setLastClockOutTime(LocalDateTime.now().minusMinutes(1));
        testingEmployee.setLastClockInTime(LocalDateTime.now());
        boolean actual = tc.checkIfClockedIn(testingEmployee);
        Assert.assertTrue(actual);
    }

    @Test
    public void isClockedIn_NotClockedIn_False() {
        testingEmployee.setLastClockInTime(LocalDateTime.now().minusMinutes(1));
        testingEmployee.setLastClockOutTime(LocalDateTime.now());
        boolean actual = tc.checkIfClockedIn(testingEmployee);
        Assert.assertFalse(actual);
    }

    @Test
    public void clockIn_UpdatingClockIn_Today() {
        LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime yesterday = today.minusDays(1);
        testingEmployee.setLastClockInTime(yesterday);
        testingEmployee.setLastClockOutTime(yesterday);
        tc.clockIn(testingEmployee);
        LocalDateTime lastClockIn = testingEmployee.getLastClockInTime().truncatedTo(ChronoUnit.DAYS);
        Assert.assertEquals(today, lastClockIn);
    }

    @Test
    public void clockOut_UpdatingClockOut_Today() {
        LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime yesterday = today.minusDays(1);
        testingEmployee.setLastClockInTime(today);
        testingEmployee.setLastClockOutTime(yesterday);
        tc.clockOut(testingEmployee);
        LocalDateTime lastClockOut = testingEmployee.getLastClockOutTime().truncatedTo(ChronoUnit.DAYS);
        Assert.assertEquals(today, lastClockOut);
    }

    @Test
    public void clockOut_IncrementingHours_True() {
        LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime yesterday = today.minusDays(1);
        testingEmployee.setLastClockInTime(yesterday);
        testingEmployee.setLastClockOutTime(yesterday);
        tc.clockOut(testingEmployee);
        Duration oneDay = Duration.ofDays(1);
        boolean atLeastOneDay = (testingEmployee.getWeeklyHours().compareTo(oneDay) >= 0);
        Assert.assertTrue(atLeastOneDay);
    }

    @Test
    public void clockIn_ResettingWeeklyHours_True() {
        LocalDateTime lastWeek = LocalDateTime.now().minusWeeks(1);
        lastWeek = lastWeek.truncatedTo(ChronoUnit.DAYS);
        testingEmployee.setLastClockOutTime(lastWeek);
        testingEmployee.setLastClockInTime(lastWeek.minusSeconds(1));
        testingEmployee.setWeeklyHours(Duration.of(1, ChronoUnit.HOURS));
        testingEmployee.setTotalHours(Duration.of(1, ChronoUnit.HOURS));
        tc.clockIn(testingEmployee);
        Assert.assertEquals(Duration.ZERO, testingEmployee.getWeeklyHours());
    }
}
