package com.mobiquityinc.utils;

import com.mobiquityinc.model.Item;

import java.util.Comparator;


/**
 * Item Comparator base on items factors.
 */
public class ItemComparator implements Comparator<Item> {


    /**
     * Compare to items and sort it in descending order based on price. If price is the same the one with lower weight
     * is ordered first.
     *
     * @param item1 First Item to compare
     * @param item2 Second Item to compare
     * @return -1 if item1 has a greater price than item2. 1 if item2 has greater prince than item1.
     */
    @Override
    public int compare(Item item1, Item item2) {
        if (item1.getPrice() == item2.getPrice())
        {
            return Float.compare(item1.getWeight(), item2.getWeight());
        } else
        {
            return Float.compare(item2.getPrice(), item1.getPrice());
        }

    }

}
