package data_structures;

public class Material {
    private String type;
    private String specific;
    private Integer number;
    private Double cost;

    public Material(String type, String specific, Integer number) {
        this.type = type;
        this.specific = specific;
        this.number = number;
        this.cost = 0.0;
    }

    public String getResourceType() {
        return type;
    }

    public String getSpecific() {
        return specific;
    }

    public void setSpecific(String resource) {
        this.specific = resource;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer num) {
        this.number = num;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

}
