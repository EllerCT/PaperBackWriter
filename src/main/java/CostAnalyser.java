import data_structures.Resource;

import java.util.List;

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
     * Totals the un-rounded cost of a set of units and resources. This method assumes
     * that the index of each unit corresponds with the index of each resource.
     * <p>
     * If the number of units does not equal the number of resources, returns -1
     *
     * @param units     A List of Integers
     * @param resources A List of Resources
     * @return The price of unit-resource pairs calculated, then summed, without rounding.
     */
    public double calculateTotalCostOf(List<Integer> units, List<Resource> resources) {
        if (units.size() == resources.size()) {
            double sum = 0.0;
            for (int i = 0; i < units.size(); i++) {
                Integer amount = units.get(i);
                if (amount == null) amount = 0;
                sum += calculateSingleCostFor(amount, resources.get(i));
            }
            return sum;
        } else {
            return -1.0;
        }
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
     * Compare a list of resources to a list of values representing the number
     * of units to compare to those resources stocked. This method assumes
     * that the indexes of each list correspond to one another.
     *
     * @param resources A List of Resource objects to compare
     * @param units     A List of Integer objects to compare against
     * @return True if every single Resource has at least 'units' many units in stock. False otherwise.
     */
    public boolean compareAll(List<Resource> resources, List<Integer> units) {
        boolean allResourcesHaveEnough = true;
        for (int i = 0; i < units.size(); i++) {
            // Break out of the loop if any is false, we don't need to see
            // how many are false.
            if (allResourcesHaveEnough == false) break;
            // Skip the current one if a resource is not provided for that index.
            if (resources.get(i) == null) continue;
            Integer amount = units.get(i);
            if (amount == null) amount = 0;
            allResourcesHaveEnough = compareStockToUnits(resources.get(i), amount);
        }
        return allResourcesHaveEnough;
    }
}
