package io_pipes;

import data_structures.Employee;
import data_structures.PinNumber;
import io_systems.IOSystem;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

public class EmployeeIOPipe extends AbstractIOPipe {
    public final static CSVFormat CSV_FORMAT = CSVFormat.EXCEL.withHeader(
            "Pin","Name","TotalHours","WeeklyHours","Points","ClockInAt","ClockOutAt");

    public EmployeeIOPipe(IOSystem ioSystem){
        super(ioSystem);
    }

    @Override
    protected CSVFormat getCsvFormat() {
        return CSV_FORMAT;
    }

    @Override
    protected Object keyMakerFor(Object object) {
        return Employee.generateKeyFor((Employee) object);
    }

    @Override
    protected Object translateFromRecord(CSVRecord record) {
        Employee currentEmployee = new Employee();
        String name = record.get("Name");
        PinNumber pin = new PinNumber(record.get("Pin"));
        Duration totalHours = Duration.parse(record.get("TotalHours"));
        Duration weeklyHours = Duration.parse(record.get("WeeklyHours"));
        Integer points = Integer.valueOf(record.get("Points"));
        LocalDateTime clockInTime = LocalDateTime.parse(record.get("ClockInAt"));
        LocalDateTime clockOutTime = LocalDateTime.parse(record.get("ClockOutAt"));
        currentEmployee.setName(name);
        currentEmployee.setPin(pin);
        currentEmployee.setPoints(points);
        currentEmployee.setLastClockInTime(clockInTime);
        currentEmployee.setLastClockOutTime(clockOutTime);
        currentEmployee.setTotalHours(totalHours);
        currentEmployee.setWeeklyHours(weeklyHours);
        return currentEmployee;
    }

    @Override
    protected void translateToRecord(CSVPrinter printer, Object outgoing) throws IOException {
        Employee employee = (Employee) outgoing;
        printer.printRecord(
                employee.getPin().toString(),
                employee.getName(),
                employee.getTotalHours().toString(),
                employee.getWeeklyHours().toString(),
                employee.getPoints(),
                employee.getLastClockInTime().toString(),
                employee.getLastClockOutTime().toString()
        );
    }
}
