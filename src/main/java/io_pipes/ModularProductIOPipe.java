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
        // The CSVParser only seperates records with a \n
        String materialsRecordString = record.get("Materials").replace("|", "\n");
        List<CSVRecord> materialRecords = new ArrayList<>();
        try {
            CSVParser parser = CSVParser.parse(materialsRecordString, MATERIAL_FORMAT.withSkipHeaderRecord());
            materialRecords = parser.getRecords();
            parser.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Material> materials = new ArrayList<>();
        for (CSVRecord materialRecord : materialRecords) {
            Integer number = 0;
            try {
                number = Integer.parseInt(materialRecord.get("Number"));
            } catch (Exception ignored) {
            }
            Material currentMaterial = new Material(materialRecord.get("Type"), materialRecord.get("Specific"), number);
            Double cost = 0.0;
            try {
                cost = Double.parseDouble(materialRecord.get("Cost"));
            } catch (Exception ignored) {
            }
            currentMaterial.setCost(cost);
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
