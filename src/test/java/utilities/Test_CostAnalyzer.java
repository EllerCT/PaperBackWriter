package utilities;

import data_structures.Resource;
import data_structures.ResourceType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Test_CostAnalyzer {

    private CostAnalyzer analyzer;
    private Resource testResource;

    @Before
    public void setUp() {
        analyzer = new CostAnalyzer();
        testResource = new Resource("Test");
        testResource.setUnitSize("Unit");
        testResource.setType(ResourceType.PAPER);
        testResource.setPricePerUnit(1.00);
    }

    @Test
    public void calculateSingleCostFor_nullIsZero_0() {
        Assert.assertEquals(0.00, analyzer.calculateSingleCostFor(0, null), 0.1);
    }

    @Test
    public void calculateSingleCostFor_Calculation_10() {
        Assert.assertEquals(10.00, analyzer.calculateSingleCostFor(10, testResource), 0.001);
    }

    @Test
    public void calculateTotalCostFromSubtotals_Calculation_10() {
        List list = Arrays.asList(1.0, 7.0, 2.0);
        double total = analyzer.calculateTotalCostFromSubtotals(list);
        Assert.assertEquals(10.00, total, 0.01);
    }

    @Test
    public void calculateTotalCostOf_Calculation_1() {
        HashMap<Resource, Integer> trivialMap = new HashMap<>();
        trivialMap.put(testResource, 1);
        double expectedCost = 1.0;
        double actualCost = analyzer.calculateTotalCostOf(trivialMap);
        Assert.assertEquals(expectedCost, actualCost, 0.1);
    }
}
