package data_structures;

import java.util.ArrayList;
import java.util.List;

public class ModularProduct {
    private static String currentID = "0";
    private List<Material> materials;
    private String id;
    private String name;
    private String type;
    private String date;
    private String description;
    private String grade;
    private String totalCost;

    public ModularProduct() {
        this.id = "";
        this.name = "";
        this.type = "";
        this.date = "";
        this.description = "";
        this.grade = "";
        this.totalCost = "0.00";
        this.materials = new ArrayList<>();
    }

    public static String getCurrentID() {
        return currentID;
    }

    public static void setCurrentID(String current) {
        currentID = current;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }
}
