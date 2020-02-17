package data_structures;

import utilities.CostAnalyzer;

public class Material {
    protected CostAnalyzer analyzer;
    private ResourceType type;
    private Resource specific;
    private Integer number;
    private Double cost;

    public Material(ResourceType type, Resource specific, Integer number) {
        analyzer = new CostAnalyzer();
        this.type = type;
        this.specific = specific;
        this.number = number;
        this.cost = analyzer.calculateSingleCostFor(number, specific);
    }

    public Material(ResourceType type) {
        analyzer = new CostAnalyzer();
        this.type = type;
        this.specific = new Resource("None");
        this.specific.setType(type);
        this.number = 0;
        cost = 0.00;
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

    public void calculateCost() {
        this.cost = analyzer.calculateSingleCostFor(number, specific);
    }

    public Double getCost() {
        return cost;
    }

    public String getFormattedCost() {
        return String.format("%.2f", cost);
    }

}
