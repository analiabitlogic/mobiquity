package com.mobiquityinc.builder;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.Item;
import com.mobiquityinc.model.Package;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class PackageBuilderTest {

    private PackageBuilder builder;

    @Before
    public void setup() {
        builder = new PackageBuilder();

    }

    @Test
    public void testBuildPackage_ValidLine() {
        String line = "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";

        try {
            Package result = builder.buildPackage(line);
            assertNotNull(result);
            assertEquals("Error during test execution.\n","4", result.indexesToString());
        } catch (APIException e) {
            fail("Error during test execution:\n" + e);
        }
    }

    @Test
    public void testBuildPackage_MissingPackageWeight() {
        String line = "(1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
        Package expectedPackage = new Package();
        List<Item> expectedItems = new ArrayList<Item>();
        Item item = new Item();
        item.setIndex(4);
        item.setPrice(Float.parseFloat("76"));
        item.setWeight(Float.parseFloat("72.30"));

        expectedItems.add(item);
        expectedPackage.setItems(expectedItems);
        try {
            Package result = builder.buildPackage(line);
            fail("Error during test execution.\n");
        } catch (APIException e) {
            assertEquals("Error during test execution:\n","Invalid content format for line.", e.getMessage());
        }
    }

    @Test
    public void testBuildPackage_MissingItems() {
        String line = "81 : ";
        Package expectedPackage = new Package();
        List<Item> expectedItems = new ArrayList<Item>();
        Item item = new Item();
        item.setIndex(4);
        item.setPrice(Float.parseFloat("76"));
        item.setWeight(Float.parseFloat("72.30"));

        expectedItems.add(item);
        expectedPackage.setItems(expectedItems);
        try {
            Package result = builder.buildPackage(line);
            fail("Error during test execution.\n");
        } catch (APIException e) {
            assertEquals("Error during test execution:\n","Invalid content format for line.", e.getMessage());
        }
    }

    @Test
    public void testBuildPackage_InvalidMaxItemsQuantity() {
        String line = "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48) (7,53.38,€45) (8,88.62,€98) (9,78.48,€3) (10,72.30,€76) (11,30.18,€9) (12,46.34,€48) (13,53.38,€45) (14,88.62,€98) (15,78.48,€3) (16,72.30,€76)";
        Package expectedPackage = new Package();
        List<Item> expectedItems = new ArrayList<Item>();
        Item item = new Item();
        item.setIndex(4);
        item.setPrice(Float.parseFloat("76"));
        item.setWeight(Float.parseFloat("72.30"));

        expectedItems.add(item);
        expectedPackage.setItems(expectedItems);
        try {
            Package result = builder.buildPackage(line);
            fail("Error during test execution.\n" + result.indexesToString());
        } catch (APIException e) {
            assertEquals("Error during test execution:\n","Invalid items quantity. Maximum number of entries exceed. 16 found but no more than 15 expected.", e.getMessage());
        }
    }

    @Test
    public void testBuildPackage_InvalidItemMaxPrice() {
        String line = "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€200) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
        Package expectedPackage = new Package();
        List<Item> expectedItems = new ArrayList<Item>();
        Item item = new Item();
        item.setIndex(4);
        item.setPrice(Float.parseFloat("76"));
        item.setWeight(Float.parseFloat("72.30"));

        expectedItems.add(item);
        expectedPackage.setItems(expectedItems);
        try {
            Package result = builder.buildPackage(line);
            fail("Error during test execution.\n");
        } catch (APIException e) {
            assertEquals("Error during test execution:\n","Invalid item price. It should be greater than 0 and up to 100.000000. Current value: 200.000000", e.getMessage());
        }
    }

    @Test
    public void testBuildPackage_InvalidItemMaxWeight() {
        String line = "81 : (1,53.38,€45) (2,200.00,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
        Package expectedPackage = new Package();
        List<Item> expectedItems = new ArrayList<Item>();
        Item item = new Item();
        item.setIndex(4);
        item.setPrice(Float.parseFloat("76"));
        item.setWeight(Float.parseFloat("72.30"));

        expectedItems.add(item);
        expectedPackage.setItems(expectedItems);
        try {
            Package result = builder.buildPackage(line);
            fail("Error during test execution.\n");
        } catch (APIException e) {
            assertEquals("Error during test execution:\n","Invalid item weight. It should be greater than 0 and up to 100.0. Current value: 200.000000", e.getMessage());
        }
    }

    @Test
    public void testBuildPackage_InvalidPackageMaxWeight() {
        String line = "200 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
        Package expectedPackage = new Package();
        List<Item> expectedItems = new ArrayList<Item>();
        Item item = new Item();
        item.setIndex(4);
        item.setPrice(Float.parseFloat("76"));
        item.setWeight(Float.parseFloat("72.30"));

        expectedItems.add(item);
        expectedPackage.setItems(expectedItems);
        try {
            Package result = builder.buildPackage(line);
            fail("Error during test execution.\n");
        } catch (APIException e) {
            assertEquals("Error during test execution:\n","Invalid package max weight. It should be up to 100.000000. Current value 200.000000", e.getMessage());
        }
    }
}
