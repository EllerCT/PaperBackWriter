import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeIO {
    private IOSystem ioSystem;

    public EmployeeIO(IOSystem ioSystem){
        this.ioSystem = ioSystem;
    }

    public Map<PinNumber, Employee> loadEmployees(){
        try {
            InputStreamReader inStreamReader = new InputStreamReader(ioSystem.read());
            CSVParser csvParser = new CSVParser(inStreamReader, CSVFormat.DEFAULT);
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

    public void addNew(Employee employee) {
        //TODO: implement addNew
    }

    public void remove(Employee employee) {
        //TODO: implement remove
    }

    public void update(Employee employee) {
        //TODO: implement update
    }
}
