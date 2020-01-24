package io_pipes;

import data_structures.Product;
import io_systems.IOSystem;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductIOPipe implements IOPipe {

    public final static CSVFormat CSV_FORMAT = CSVFormat.EXCEL.withHeader(
            "ID",
            "Name",
            "Type",
            "Date",
            "Description",
            "Grade",
            "Total Cost",
            "Paper Type",
            "Amount of Paper",
            "Paper Cost",
            "Thread Type",
            "Amount of Thread",
            "Thread Cost",
            "Glue Type",
            "Amount of Glue",
            "Glue Cost",
            "Board Type",
            "Amount of Board",
            "Board Cost",
            "Decorated Paper Type",
            "Amount of Decorated Paper",
            "Decorated Paper Cost",
            "Spine Type",
            "Amount of Spine",
            "Spine Cost",
            "End Band Type",
            "Amount of End Band",
            "End Band Cost",
            "Other",
            "Amount of Other",
            "Other Cost",
            "Mineral Spirit Type",
            "Amount of Mineral Spirit",
            "Mineral Spirit Cost");
    private IOSystem ioSystem;

    public ProductIOPipe(IOSystem ioSystem) {
        this.ioSystem = ioSystem;
    }

    public Map<String, Product> load() {
        try {
            InputStreamReader isr = new InputStreamReader(ioSystem.read());
            CSVParser csvParser = new CSVParser(isr, CSV_FORMAT.withSkipHeaderRecord());
            HashMap<String, Product> productMap = new HashMap<>();
            List<CSVRecord> records = csvParser.getRecords();
            if (records.size() > 0) {
                for (CSVRecord record : records) {
                    Product product = translateProductFromRecord(record);
                    productMap.put(Product.generateKeyFor(product), product);
                }
            } else {
                makeProductsFile();
            }
            csvParser.close();
            return productMap;
        } catch (IOException e) {
            System.err.println("Failed to load products, using an empty set.");
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    private Product translateProductFromRecord(CSVRecord record) {
        Product product = new Product();
        product.setId(record.get("ID"));
        product.setName(record.get("Name"));
        product.setType(record.get("Type"));
        product.setDate(record.get("Date"));
        product.setDescription(record.get("Description"));
        product.setGrade(record.get("Grade"));
        product.setTotalCost(record.get("Total Cost"));
        product.setPaperType(record.get("Paper Type"));
        product.setPaperAmount(record.get("Amount of Paper"));
        product.setPaperCost(record.get("Paper Cost"));
        product.setThreadType(record.get("Thread Type"));
        product.setThreadAmount(record.get("Amount of Thread"));
        product.setThreadCost(record.get("Thread Cost"));
        product.setGlueType(record.get("Glue Type"));
        product.setGlueAmount(record.get("Amount of Glue"));
        product.setGlueCost(record.get("Glue Cost"));
        product.setBoardType(record.get("Board Type"));
        product.setBoardAmount(record.get("Amount of Board"));
        product.setBoardCost(record.get("Board Cost"));
        product.setDecoratedPaperType(record.get("Decorated Paper Type"));
        product.setDecoratedPaperAmount(record.get("Amount of Decorated Paper"));
        product.setDecoratedPaperCost(record.get("Decorated Paper Cost"));
        product.setSpineType(record.get("Spine Type"));
        product.setSpineAmount(record.get("Amount of Spine"));
        product.setSpineCost(record.get("Spine Cost"));
        product.setEndBandType(record.get("End Band Type"));
        product.setEndBandAmount(record.get("Amount of End Band"));
        product.setEndBandCost(record.get("End Band Cost"));
        product.setOther(record.get("Other"));
        product.setOtherAmount(record.get("Amount of Other"));
        product.setOtherCost(record.get("Other Cost"));
        product.setSpiritType(record.get("Mineral Spirit Type"));
        product.setSpiritAmount(record.get("Amount of Mineral Spirit"));
        product.setSpiritCost(record.get("Mineral Spirit Cost"));
        return product;
    }

    private void makeProductsFile() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CSVPrinter printer = new CSVPrinter(new BufferedWriter(new OutputStreamWriter(outputStream)), CSV_FORMAT.withSkipHeaderRecord());
        printer.printRecords();
        printer.flush();
        ioSystem.write(outputStream.toByteArray());
    }

    public void save(Map map) {
        HashMap<String, Product> productsMap = (HashMap<String, Product>) map;
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(byteStream));
            CSVPrinter printer = new CSVPrinter(writer, CSV_FORMAT);
            for (Map.Entry<String, Product> entry : productsMap.entrySet()) {
                Product product = entry.getValue();
                printer.printRecord(
                        product.getId(),
                        product.getName(),
                        product.getType(),
                        product.getDate(),
                        product.getDescription(),
                        product.getGrade(),
                        product.getTotalCost(),
                        product.getPaperType(),
                        product.getPaperAmount(),
                        product.getPaperCost(),
                        product.getThreadType(),
                        product.getThreadAmount(),
                        product.getThreadCost(),
                        product.getGlueType(),
                        product.getGlueAmount(),
                        product.getGlueCost(),
                        product.getBoardType(),
                        product.getBoardAmount(),
                        product.getBoardCost(),
                        product.getDecoratedPaperType(),
                        product.getDecoratedPaperAmount(),
                        product.getDecoratedPaperCost(),
                        product.getSpineType(),
                        product.getSpineAmount(),
                        product.getSpineCost(),
                        product.getEndBandType(),
                        product.getEndBandAmount(),
                        product.getEndBandCost(),
                        product.getOther(),
                        product.getOtherAmount(),
                        product.getOtherCost(),
                        product.getSpiritType(),
                        product.getSpiritAmount(),
                        product.getSpiritCost()
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
