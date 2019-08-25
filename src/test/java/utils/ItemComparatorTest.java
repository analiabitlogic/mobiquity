package utils;

import com.mobiquityinc.model.Item;
import com.mobiquityinc.utils.ItemComparator;
import org.junit.Before;
import org.junit.Test;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class ItemComparatorTest {
    private ItemComparator comparator;

    @Before
    public void setup() {
        comparator = new ItemComparator();
    }

    @Test
    public void testCompare_Item2GreaterPrice() {
        Item item1 = new Item();
        Item item2 = new Item();
        item1.setIndex(1);
        item1.setWeight(94.00f);
        item1.setPrice(20.00f);

        item2.setIndex(1);
        item2.setWeight(94.00f);
        item2.setPrice(30.00f);

        int result = comparator.compare(item1, item2);

        assertEquals("Error during execution test. \n", 1, result);

        SortedSet<Item> items = new TreeSet<>(new ItemComparator());
        items.add(item1);
        items.add(item2);

        assertEquals("Error during test execution.\n", items.first(), item2);

    }

    @Test
    public void testCompare_Item1GreaterPrice() {
        Item item1 = new Item();
        Item item2 = new Item();
        item1.setIndex(1);
        item1.setWeight(94.00f);
        item1.setPrice(30.00f);

        item2.setIndex(1);
        item2.setWeight(94.00f);
        item2.setPrice(20.00f);

        int result = comparator.compare(item1, item2);

        assertEquals("Error during execution test. \n", -1, result);

        SortedSet<Item> items = new TreeSet<>(new ItemComparator());
        items.add(item1);
        items.add(item2);

        assertEquals("Error during test execution.\n", items.first(), item1);

    }

    @Test
    public void testCompare_SamePrice_Item2LowerWeight() {
        Item item1 = new Item();
        Item item2 = new Item();
        item1.setIndex(1);
        item1.setWeight(94.00f);
        item1.setPrice(20.00f);

        item2.setIndex(1);
        item2.setWeight(34.00f);
        item2.setPrice(20.00f);

        int result = comparator.compare(item1, item2);

        assertEquals("Error during execution test. \n", 1, result);

        SortedSet<Item> items = new TreeSet<>(new ItemComparator());
        items.add(item1);
        items.add(item2);

        assertEquals("Error during test execution.\n", items.first(), item2);
    }

    @Test
    public void testCompare_SamePrice_Item1LowerWeight() {
        Item item1 = new Item();
        Item item2 = new Item();
        item1.setIndex(1);
        item1.setWeight(34.00f);
        item1.setPrice(20.00f);

        item2.setIndex(1);
        item2.setWeight(94.00f);
        item2.setPrice(20.00f);

        int result = comparator.compare(item1, item2);

        assertEquals("Error during execution test. \n", -1, result);

        SortedSet<Item> items = new TreeSet<>(new ItemComparator());
        items.add(item1);
        items.add(item2);

        assertEquals("Error during test execution.\n", items.first(), item1);
    }

    @Test
    public void testCompare_SamePrice_SameWeight() {
        Item item1 = new Item();
        Item item2 = new Item();
        item1.setIndex(1);
        item1.setWeight(94.00f);
        item1.setPrice(20.00f);

        item2.setIndex(1);
        item2.setWeight(94.00f);
        item2.setPrice(20.00f);

        int result = comparator.compare(item1, item2);

        assertEquals("Error during execution test. \n", 0, result);

        SortedSet<Item> items = new TreeSet<>(new ItemComparator());
        items.add(item1);
        items.add(item2);

        assertEquals("Error during test execution.\n", items.first(), item1);
    }
}
