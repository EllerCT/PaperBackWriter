import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeIOPipe {
    private final static CSVFormat csvFormat = CSVFormat.ORACLE.withHeader("Pin","Name","Hours","Points","ClockInAt");

    private IOSystem ioSystem;

    public EmployeeIOPipe(IOSystem ioSystem){
        this.ioSystem = ioSystem;
    }

    public Map<PinNumber, Employee> loadEmployees(){
        try {
            InputStreamReader inStreamReader = new InputStreamReader(ioSystem.read());
            CSVParser csvParser = new CSVParser(inStreamReader, csvFormat);
            List<CSVRecord> records = csvParser.getRecords();
            Map<PinNumber, Employee> employeeMap = new HashMap<>();
            for (CSVRecord record : records){
                Employee currentEmployee = translateRecordToEmployee(record);
                employeeMap.put(currentEmployee.getPin(), currentEmployee);
            }
            csvParser.close();
            return employeeMap;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    private Employee translateRecordToEmployee(CSVRecord record) {
        Employee currentEmployee = new Employee();
        String name = record.get("Name");
        PinNumber pin = new PinNumber(record.get("Pin"));
        Duration hours = Duration.parse(record.get("Hours"));
        Integer points = Integer.valueOf(record.get("Points"));
        LocalDateTime clockInTime = LocalDateTime.parse(record.get("ClockInAt"));
        currentEmployee.setName(name);
        currentEmployee.setPin(pin);
        currentEmployee.setPoints(points);
        currentEmployee.setClockInTime(clockInTime);
        currentEmployee.setHours(hours);
        return currentEmployee;
    }

    public void saveEmployees(Map<PinNumber, Employee> employeeMap){
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            OutputStreamWriter stream = new OutputStreamWriter(byteStream);
            CSVPrinter printer = new CSVPrinter(stream, csvFormat);
            for (Map.Entry<PinNumber, Employee> entry : employeeMap.entrySet()) {
                Employee employee = entry.getValue();
                printer.printRecords(
                        employee.getPin().toString(),
                        employee.getName(),
                        employee.getHours().toString(),
                        employee.getPoints(),
                        employee.getClockInTime().toString()
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
