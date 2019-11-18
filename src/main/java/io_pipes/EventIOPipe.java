package io_pipes;

import data_structures.Event;
import io_systems.IOSystem;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventIOPipe {
    private final static CSVFormat csvFormat = CSVFormat.EXCEL.withHeader(
            "Code", "Worth", "Name", "Description");
    private IOSystem ioSystem;

    public EventIOPipe(IOSystem ioSystem) {
        this.ioSystem = ioSystem;
    }

    public Map<String, Event> loadEvents() {
        try {
            InputStreamReader isr = new InputStreamReader(ioSystem.read());
            CSVParser csvParser = new CSVParser(isr, csvFormat.withSkipHeaderRecord());
            HashMap<String, Event> events = new HashMap<>();
            List<CSVRecord> records = csvParser.getRecords();
            if (records.size() > 0) {
                for (CSVRecord record : records) {
                    Event event = translateEventFromRecord(record);
                    events.put(event.getEventCode(), event);
                }
            } else {
                makeNewEventFile();
            }
            csvParser.close();
            return events;
        } catch (IOException e) {
            System.err.println("Failed to load events.");
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    private void makeNewEventFile() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CSVPrinter printer = new CSVPrinter(new BufferedWriter(new OutputStreamWriter(outputStream)), csvFormat.withSkipHeaderRecord());
        printer.printRecords();
        printer.flush();
        ioSystem.write(outputStream.toByteArray());
    }

    private Event translateEventFromRecord(CSVRecord record) {
        Event event = new Event(record.get("Code"), Integer.parseInt(record.get("Worth")));
        event.setEventName(record.get("Name"));
        event.setEventDescription(record.get("Description"));
        return event;
    }

    public void saveEvents(Map<String, Event> events) {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(byteStream));
            CSVPrinter printer = new CSVPrinter(writer, csvFormat);
            for (Map.Entry<String, Event> entry : events.entrySet()) {
                Event event = entry.getValue();
                printer.printRecord(
                        event.getEventCode(),
                        event.getPointWorth(),
                        event.getEventName(),
                        event.getEventDescription()
                );
            }
            printer.flush();
            byte[] outBytes = byteStream.toByteArray();
            printer.close();
            ioSystem.write(outBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
