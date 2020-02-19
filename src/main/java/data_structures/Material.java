package data_structures;

public class Material {
    private ResourceType type;
    private Resource specific;
    private Integer number;
    private Double cost;

    public Material(ResourceType type, Resource specific, Integer number) {
        this.type = type;
        this.specific = specific;
        this.number = number;
        this.cost = 0.0;
    }

    public Material(ResourceType type) {
        this.type = type;
        this.specific = new Resource("None");
        this.specific.setType(type);
        this.number = 0;
        this.cost = 0.00;
    }

    public ResourceType getResourceType() {
        return type;
    }

    public Resource getSpecific() {
        return specific;
    }

    public void setSpecific(Resource resource) {
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
