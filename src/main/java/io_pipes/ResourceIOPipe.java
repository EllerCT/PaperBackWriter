package io_pipes;

import data_structures.Resource;
import data_structures.ResourceType;
import io_systems.IOSystem;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;

public class ResourceIOPipe extends AbstractIOPipe {

    public final static CSVFormat CSV_FORMAT = CSVFormat.EXCEL.withHeader("Type", "Name", "Unit Size", "Price Per Unit");

    public ResourceIOPipe(IOSystem ioSystem) {
        super(ioSystem);
    }

    @Override
    protected CSVFormat getCsvFormat() {
        return CSV_FORMAT;
    }

    @Override
    protected Object keyMakerFor(Object object) {
        return Resource.generateKeyFor((Resource) object);
    }

    @Override
    protected Object translateFromRecord(CSVRecord record) {
        Resource resource = new Resource(record.get("Name"));
        resource.setPricePerUnit(Double.parseDouble(record.get("Price Per Unit")));
        resource.setType(ResourceType.parseString(record.get("Type")));
        resource.setUnitSize(record.get("Unit Size"));
        return resource;
    }

    @Override
    protected void translateToRecord(CSVPrinter printer, Object outgoing) throws IOException {
        Resource resource = (Resource) outgoing;
        printer.printRecord(
                resource.getType(),
                resource.getName(),
                resource.getUnitSize(),
                resource.getPricePerUnit()
        );
    }
}
