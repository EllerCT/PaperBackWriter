package io_pipes;

import data_structures.Event;
import io_systems.IOSystem;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;

public class EventIOPipe extends AbstractIOPipe {
    private final static CSVFormat CSV_FORMAT = CSVFormat.EXCEL.withHeader(
            "Code", "Worth", "Name", "Description", "Confirmation");

    public EventIOPipe(IOSystem ioSystem) {
        super(ioSystem);
    }

    @Override
    protected CSVFormat getCsvFormat() {
        return CSV_FORMAT;
    }

    @Override
    protected Object keyMakerFor(Object object) {
        return Event.generateKeyFor((Event) object);
    }

    @Override
    protected Object translateFromRecord(CSVRecord record) {
        Event event = new Event(record.get("Code"), Integer.parseInt(record.get("Worth")));
        event.setEventName(record.get("Name"));
        event.setEventDescription(record.get("Description"));
        event.setEventConfirmationCode(record.get("Confirmation"));
        return event;
    }

    @Override
    protected void translateToRecord(CSVPrinter printer, Object object) throws IOException {
        Event event = (Event) object;
        printer.printRecord(
                event.getEventCode(),
                event.getPointWorth(),
                event.getEventName(),
                event.getEventDescription(),
                event.getEventConfirmationCode()
        );
    }
}
