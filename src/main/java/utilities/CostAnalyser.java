package utilities;

import data_structures.Resource;

import java.util.List;
import java.util.Map;

public class CostAnalyser {

    /**
     * Yield the price of a number of units of the given resource, without sales tax
     * included in the calculation.
     *
     * @param resource Resource
     * @param units    Integer
     * @return The price of 'units' of 'resource' without rounding.
     */
    public double calculateSingleCostFor(int units, Resource resource) {
        if (resource == null || units == 0) return 0.0;
        return resource.getPricePerUnit() * (double) units;
    }

    /**
     * Sum the combined cost of Integer units multiplied by the unit cost of their
     * corresponding Resource resources.
     * @param resourceUnitPairs A Map whose key is a resource, and value is an Integer.
     * @return An unrounded decimal sum of the total costs.
     */
    public double calculateTotalCostOf(Map<Resource, Integer> resourceUnitPairs) {
        double sum = 0.0;
        for (Resource resource : resourceUnitPairs.keySet()) {
            Integer amount = resourceUnitPairs.get(resource);
            if (amount == null) amount = 0;
            sum += calculateSingleCostFor(amount, resource);
        }
        return sum;
    }

    /**
     * An alternative means of calculating an unrounded total from a list of already
     * calculated subtotals.
     *
     * @param costs A list of decimals from which to generate a total
     * @return The total of the given list, unrounded.
     */
    public double calculateTotalCostFromSubtotals(List<Double> costs) {
        double sum = 0.0;
        for (Double cost : costs) {
            sum += cost;
        }
        return sum;
    }
}
