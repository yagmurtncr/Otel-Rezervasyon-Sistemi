
package com.example.otelrezervasyonsistemi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class FoodListItem {
    private String name; // Yiyecek adı
    private double price; // Yiyecek fiyatı

    private ArrayList<String> items = new ArrayList<>();

    // Constructor metodu
    public FoodListItem(String name, double price, ArrayList<String> items) {
        this.name = name;
        this.price = price;
        this.items = items;
    }

    // Yiyecek adını döndüren metot
    public String getName() {
        return name;
    }

    // Yiyecek fiyatını döndüren metot
    public double getPrice() {
        return price;
    }

    public ArrayList<String> getItems() {
        return this.items;
    }

    // Yiyecek içeriğini döndüren metot


    // toString metodu, yiyeceğin adını ve fiyatını birleştirerek döndürür
    @Override
    public String toString() {
        return name + " - $" + price;
    }
}


