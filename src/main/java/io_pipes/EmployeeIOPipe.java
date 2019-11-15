package io_pipes;

import data_structures.Employee;
import data_structures.PinNumber;
import io_systems.IOSystem;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeIOPipe {
    private final static CSVFormat csvFormat = CSVFormat.ORACLE.withHeader(
            "Pin","Name","TotalHours","WeeklyHours","Points","ClockInAt","ClockOutAt");

    private IOSystem ioSystem;

    public EmployeeIOPipe(IOSystem ioSystem){
        this.ioSystem = ioSystem;
    }

    public Map<PinNumber, Employee> loadEmployees(){
        try {
            InputStreamReader inStreamReader = new InputStreamReader(ioSystem.read());
            CSVParser csvParser = new CSVParser(inStreamReader, csvFormat.withSkipHeaderRecord());
            List<CSVRecord> records = csvParser.getRecords();
            Map<PinNumber, Employee> employeeMap = new HashMap<>();
            if (records.size() > 1) {
                for (CSVRecord record : records) {
                    Employee currentEmployee = translateRecordToEmployee(record);
                    employeeMap.put(currentEmployee.getPin(), currentEmployee);
                }
            } else {
                makeNewEmployeeFile();
            }
            csvParser.close();
            return employeeMap;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    private void makeNewEmployeeFile() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        CSVPrinter printer = new CSVPrinter(new BufferedWriter(new OutputStreamWriter(stream)), csvFormat.withSkipHeaderRecord());
        printer.printRecords();
        printer.flush();
        ioSystem.write(stream.toByteArray());
    }

    private Employee translateRecordToEmployee(CSVRecord record) {
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

    public void saveEmployees(Map<PinNumber, Employee> employeeMap){
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            BufferedWriter stream = new BufferedWriter(new OutputStreamWriter(byteStream));
            CSVPrinter printer = new CSVPrinter(stream, csvFormat);
            for (Map.Entry<PinNumber, Employee> entry : employeeMap.entrySet()) {
                Employee employee = entry.getValue();
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
            printer.flush();
            byte[] outBytes = byteStream.toByteArray();
            printer.close();
            ioSystem.write(outBytes);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
