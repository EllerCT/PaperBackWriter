package io_pipes;

import data_structures.Material;
import data_structures.ModularProduct;
import io_systems.IOSystem;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModularProductIOPipe extends AbstractIOPipe {
    public final static CSVFormat CSV_FORMAT = CSVFormat.EXCEL.withHeader(
            "ID",
            "Name",
            "Type",
            "Date",
            "Description",
            "Grade",
            "Total Cost",
            "Materials"
    );
    private final static CSVFormat MATERIAL_FORMAT = CSVFormat.DEFAULT.withRecordSeparator('|').withHeader(
            "Type",
            "Specific",
            "Number",
            "Cost"
    );

    public ModularProductIOPipe(IOSystem ioSystem) {
        super(ioSystem);
    }

    @Override
    protected CSVFormat getCsvFormat() {
        return CSV_FORMAT;
    }

    @Override
    protected Object keyMakerFor(Object object) {
        return ModularProduct.generateKeyFor((ModularProduct) object);
    }

    @Override
    protected Object translateFromRecord(CSVRecord record) {
        ModularProduct modularProduct = new ModularProduct();
        modularProduct.setId(record.get("ID"));
        modularProduct.setName(record.get("Name"));
        modularProduct.setType(record.get("Type"));
        modularProduct.setDate(record.get("Date"));
        modularProduct.setDescription(record.get("Description"));
        modularProduct.setGrade(record.get("Grade"));
        modularProduct.setTotalCost(record.get("Total Cost"));
        String materialsRecordString = record.get("Materials");
        List<CSVRecord> materialRecords = new ArrayList<>();
        try {
            materialRecords = CSVParser.parse(materialsRecordString, MATERIAL_FORMAT).getRecords();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Material> materials = new ArrayList<>();
        for (CSVRecord materialRecord : materialRecords) {
            Material currentMaterial = new Material(materialRecord.get("Type"), materialRecord.get("Specific"), Integer.parseInt(materialRecord.get("Number")));
            currentMaterial.setCost(Double.parseDouble(materialRecord.get("Cost")));
            materials.add(currentMaterial);
        }
        modularProduct.setMaterials(materials);
        return modularProduct;
    }

    @Override
    protected void translateToRecord(CSVPrinter printer, Object object) throws IOException {
        ModularProduct outgoing = (ModularProduct) object;
        String materialRecord = makeMaterialRecordFor(outgoing);
        printer.printRecord(
                outgoing.getId(),
                outgoing.getName(),
                outgoing.getType(),
                outgoing.getDate(),
                outgoing.getDescription(),
                outgoing.getGrade(),
                outgoing.getTotalCost(),
                materialRecord
        );
    }

    private String makeMaterialRecordFor(ModularProduct outgoing) {
        List<Material> materials = outgoing.getMaterials();
        String recordString = "";
        try {
            CSVPrinter printer = new CSVPrinter(new StringBuilder(), MATERIAL_FORMAT);
            for (Material material : materials) {
                printer.printRecord(
                        material.getResourceType(),
                        material.getSpecific(),
                        material.getNumber(),
                        material.getCost()
                );
            }
            printer.flush();
            recordString = printer.getOut().toString();
            printer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recordString;
    }
}
