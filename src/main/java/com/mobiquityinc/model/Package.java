package com.mobiquityinc.model;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * The Package class.
 *
 */
public class Package {
    private List<Item> items = new ArrayList<Item>();

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String indexesToString() {
        return items.stream().map(i -> String.valueOf(i.getIndex())).collect(Collectors.joining(","));
    }
}
