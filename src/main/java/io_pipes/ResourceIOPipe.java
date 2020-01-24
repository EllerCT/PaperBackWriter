package io_pipes;

import data_structures.Resource;
import data_structures.ResourceType;
import io_systems.IOSystem;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceIOPipe implements IOPipe {

    public final static CSVFormat CSV_FORMAT = CSVFormat.EXCEL.withHeader("Type", "Name", "Unit Size", "Price Per Unit", "Units In Stock");
    private IOSystem ioSystem;

    public ResourceIOPipe(IOSystem ioSystem) {
        this.ioSystem = ioSystem;
    }

    public Map<String, Resource> load() {
        try {
            InputStreamReader isr = new InputStreamReader(ioSystem.read());
            CSVParser csvParser = new CSVParser(isr, CSV_FORMAT.withSkipHeaderRecord());
            HashMap<String, Resource> resourceMap = new HashMap<>();
            List<CSVRecord> records = csvParser.getRecords();
            if (records.size() > 0) {
                for (CSVRecord record : records) {
                    Resource resource = translateResourceFromRecord(record);
                    resourceMap.put(Resource.generateKeyFor(resource), resource);
                }
            } else {
                makeResourceFile();
            }
            csvParser.close();
            return resourceMap;
        } catch (IOException e) {
            System.err.println("Failed to load resources, using an empty set.");
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    private Resource translateResourceFromRecord(CSVRecord record) {
        Resource resource = new Resource(record.get("Name"));
        resource.setPricePerUnit(Double.parseDouble(record.get("Price Per Unit")));
        resource.setType(ResourceType.parseString(record.get("Type")));
        resource.setUnitSize(record.get("Unit Size"));
        resource.setUnitsInStock(Integer.parseInt(record.get("Units In Stock")));
        return resource;
    }

    private void makeResourceFile() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CSVPrinter printer = new CSVPrinter(new BufferedWriter(new OutputStreamWriter(outputStream)), CSV_FORMAT.withSkipHeaderRecord());
        printer.printRecords();
        printer.flush();
        ioSystem.write(outputStream.toByteArray());
    }

    public void save(Map map) {
        HashMap<String, Resource> resourceMap = (HashMap<String, Resource>) map;
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(byteStream));
            CSVPrinter printer = new CSVPrinter(writer, CSV_FORMAT);
            for (Map.Entry<String, Resource> entry : resourceMap.entrySet()) {
                Resource resource = entry.getValue();
                printer.printRecord(
                        resource.getType(),
                        resource.getName(),
                        resource.getUnitSize(),
                        resource.getPricePerUnit(),
                        resource.getUnitsInStock()
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
