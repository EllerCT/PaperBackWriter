package data_structures;

import java.util.Objects;

public class Resource {
    private ResourceType type;
    private String unitSize;
    private double pricePerUnit;
    private String name;
    private int unitsInStock;

    public Resource(String name) {
        this.name = name;
        this.unitSize = "";
        this.pricePerUnit = 0.00;
        this.unitsInStock = 0;
    }

    public int getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(int unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public String getName() {
        return name;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public String getUnitSize() {
        return unitSize;
    }

    public void setUnitSize(String unitSize) {
        this.unitSize = unitSize;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    @Override
    public String toString() {
        return name + " (" + unitSize + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Double.compare(resource.pricePerUnit, pricePerUnit) == 0 &&
                type == resource.type &&
                unitSize.equals(resource.unitSize) &&
                name.equals(resource.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, unitSize, pricePerUnit, name);
    }
}
