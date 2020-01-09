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

    /**
     * If the given resource has enough units in stock, this method will reduce the stock
     * by the given number of units and return 'true'. If the requested number of
     * units exceeds the resources in stock, it will return false.
     *
     * @param unit     Integer to subtract
     * @param resource Resource being subtracted from
     * @return True if operation completed successfully, otherwise false.
     */
    public boolean consume(int unit, Resource resource) {
        if (unit >= resource.getUnitsInStock()) {
            int inStock = resource.getUnitsInStock();
            int left = inStock - unit;
            resource.setUnitsInStock(left);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if a resource hs at least a certain number of units.
     *
     * @param resource Resource to check stock
     * @param unit     Integer to check against
     * @return True if resource has at least 'unit' units left in stock.
     */
    public boolean compareStockToUnits(Resource resource, int unit) {
        return resource.getUnitsInStock() >= unit;
    }


    /**
     * Compare stocked resources paired to a desired Integer, to determine if all
     * given resources have at least that number in stock. Returns true if
     * all resources have at least their paired Integer stocked, false otherwise.
     * @param resourceUnitPairs A map of resources, and integers paired with them.
     * @return True if all resources have sufficient stock, otherwise false.
     */
    public boolean compareAll(Map<Resource, Integer> resourceUnitPairs) {
        boolean allResourcesHaveEnough = true;
        for (Resource resource : resourceUnitPairs.keySet()) {
            // Break out of the loop if any is false, we don't need to see
            // how many are false.
            if (allResourcesHaveEnough == false) break;
            Integer amount = resourceUnitPairs.get(resource);
            if (amount == null) amount = 0;
            allResourcesHaveEnough = compareStockToUnits(resource, amount);
        }
        return allResourcesHaveEnough;
    }
}
