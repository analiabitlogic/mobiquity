package com.mobiquityinc.builder;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.Item;
import com.mobiquityinc.utils.ItemComparator;
import com.mobiquityinc.model.Package;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Builder for a Package.
 *
 * The construction of a package is quite a complex so that the builder helps to create different packages using the same construction process.
 *
 */
public class PackageBuilder {
    private static final float MAX_PACKAGE_WEIGHT = 100f;
    private static final float MAX_ITEM_WEIGHT = 100f;
    private static final float MAX_ITEM_PRICE = 100f;
    private static final int MAX_ITEMS_PER_LINE = 15;

    private static final int ITEM_INDEX_GROUP = 1;
    private static final int ITEM_WEIGHT_GROUP = 2;
    private static final int ITEM_PRICE_GROUP = 3;

    private static final int FILE_PACKAGE_MAX_WEIGHT = 0;
    private static final int FILE_PACKAGE_ITEMS = 1;

    /**
     * Put items on package maximizing price up to the max package weight.
     * @param currentLine Line
     * @return Package of items
     * @throws APIException Error during internal processing.
     */
    public static Package buildPackage(String currentLine) throws APIException {
        String[] lineParts = getLineParts(currentLine);
        float packageMaxWeight = getPackageMaxWeight(lineParts[FILE_PACKAGE_MAX_WEIGHT]);
        SortedSet<Item> items = getPackageItems(lineParts[FILE_PACKAGE_ITEMS]);
        return buildPackage(packageMaxWeight, items);
    }

    /**
     * Put items on package maximizing price up to the max package weight.
     * @param packageMaxWeight Package max weight
     * @param availableItems Set of available items sorted by price in descending order
     * @return Package of items
     */
    private static Package buildPackage(float packageMaxWeight, SortedSet<Item> availableItems) {
        Package itemsPackage = new Package();
        float currentWeight = 0f;
        Iterator<Item> items = availableItems.iterator();
        List<Item> listOfItemsToPack = new ArrayList<>();

        while (items.hasNext()) {
            Item item = items.next();
            if (item.getWeight() + currentWeight <= packageMaxWeight) {
                currentWeight += item.getWeight();
                listOfItemsToPack.add(item);
            }
        }
        itemsPackage.setItems(listOfItemsToPack);
        return itemsPackage;
    }

    /**
     * Process file line by separating Package weight from list of items.
     * @param currentLine File line.
     * @return String array containing the package max weight and the list of items.
     * @throws APIException Error if file is not well structured.
     */
    private static String[] getLineParts(String currentLine) throws APIException {
        String[] content = currentLine.split("\\s:\\s");

        if (content.length != 2)
            throw new APIException("Invalid content format for line.", null);

        return content;
    }

    /**
     * Get Package max weight
     * @param packageMaxWeightLine line part containing package max weight
     * @return Max weight
     * @throws APIException Error if package max weight can´t be recovered from line.
     */
    private static float getPackageMaxWeight(String packageMaxWeightLine) throws APIException {
        float packageMaxWeight = Float.parseFloat(packageMaxWeightLine);
        if (packageMaxWeight> MAX_PACKAGE_WEIGHT)
            throw new APIException(String.format("Invalid package max weight. It should be up to %f. Current value %f", MAX_PACKAGE_WEIGHT, packageMaxWeight), null);
        return packageMaxWeight;
    }

    /**
     * Get set of items sorted by price
     * @param packageItemLine String containing list of items to be parsed.
     * @return Sorted set of items ordered by price.
     * @throws APIException Error if line format is incorrect.
     */
    private static SortedSet<Item> getPackageItems(String packageItemLine) throws APIException {
        String regex = "(?:\\(([\\d]{1,2}),(\\d{1,3}\\.?\\d{0,2}),€(\\d{1,3})\\))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(packageItemLine);

        SortedSet<Item> items = new TreeSet<>(new ItemComparator());
        for (int i = 1; matcher.find(); i++) {
            if ( i > MAX_ITEMS_PER_LINE)
                throw new APIException(String.format("Invalid items quantity. Maximum number of entries exceed. %d found but no more than %d expected.", i, MAX_ITEMS_PER_LINE), null);

            if (matcher.groupCount() != 3)
                throw new APIException(String.format("Invalid item content. Incorrect number of entries found. %d found but 3 expected.", matcher.groupCount()), null);

            Item item = new Item();
            item.setIndex(Integer.parseInt(matcher.group(ITEM_INDEX_GROUP)));
            item.setWeight(Float.parseFloat(matcher.group(ITEM_WEIGHT_GROUP)));
            item.setPrice(Float.parseFloat(matcher.group(ITEM_PRICE_GROUP)));

            if (item.getWeight() <= 0 || item.getWeight() > MAX_ITEM_WEIGHT )
                throw new APIException(String.format("Invalid item weight. It should be greater than 0 and up to %s. Current value: %f", MAX_ITEM_WEIGHT, item.getWeight()), null);

            if (item.getPrice() <= 0 || item.getPrice() > MAX_ITEM_PRICE)
                throw new APIException(String.format("Invalid item price. It should be greater than 0 and up to %f. Current value: %f", MAX_ITEM_PRICE, item.getPrice()), null);

            items.add(item);
        }
        return items;
    }
}
