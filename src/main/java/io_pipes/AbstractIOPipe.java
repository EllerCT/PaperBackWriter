package io_pipes;

import io_systems.IOSystem;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractIOPipe implements IOPipe {

    protected IOSystem ioSystem;

    public AbstractIOPipe(IOSystem ioSystem) {
        this.ioSystem = ioSystem;
    }

    @Override
    public Map load() {
        try {
            InputStreamReader isr = new InputStreamReader(ioSystem.read());
            CSVParser csvParser = new CSVParser(isr, this.getCsvFormat().withSkipHeaderRecord());
            HashMap<Object, Object> map = new HashMap<>();
            List<CSVRecord> records = csvParser.getRecords();
            if (records.size() > 0) {
                for (CSVRecord record : records) {
                    Object translation = translateFromRecord(record);
                    map.put(keyMakerFor(translation), translation);
                }
            }
            csvParser.close();
            return map;
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
            return new HashMap<>();
        }
    }

    @Override
    public void save(Map map) {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(byteStream));
            CSVPrinter printer = new CSVPrinter(writer, this.getCsvFormat());
            for (Object entry : map.values()) {
                translateToRecord(printer, entry);
            }
            printer.flush();
            byte[] outBytes = byteStream.toByteArray();
            printer.close();
            ioSystem.write(outBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract CSVFormat getCsvFormat();

    protected abstract Object keyMakerFor(Object object);

    protected abstract Object translateFromRecord(CSVRecord record);

    protected abstract void translateToRecord(CSVPrinter printer, Object object) throws IOException;

    protected void makeNewFile() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CSVPrinter printer = new CSVPrinter(new BufferedWriter(new OutputStreamWriter(outputStream)), getCsvFormat().withSkipHeaderRecord());
        printer.printRecords();
        printer.flush();
        ioSystem.write(outputStream.toByteArray());
    }
}
