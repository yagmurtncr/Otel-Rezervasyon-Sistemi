package com.example.otelrezervasyonsistemi; /*****
package com.example.otelrezervasyonsistemi;

import java.util.*;
import java.util.LinkedList;

import java.util.Queue;

    public class FoodList {


        private Queue<FoodListItem> items; // Yiyecek listesi için bir kuyruk
        private ArrayList<FoodListItem> items_arr= new ArrayList<>();

        public FoodList() {
            this.items = new LinkedList<>(); // Kuyruğu bir LinkedList ile başlatıyoruz.

            // Varsayılan yiyecek öğelerini ekliyoruz.
            items.add(new FoodListItem("Breakfast", 90.0));
            items.add(new FoodListItem("Lunch", 120.0));
            items.add(new FoodListItem("Dinner", 130.0));
        }

        // Yiyecek eklemek için bir metot
        public void addItem(FoodListItem item) {
            items.add(item); // Yiyecekleri kuyruğa ekliyoruz.
        }

        // Tüm yiyecekleri almak için bir metot
        public ArrayList<FoodListItem> getItems() {
            for (int i = 0; i < 3; i++) {
                // System.out.println(items.poll());
                items_arr.add(items.poll());
            }
            //return items; // Yiyecek kuyruğunu döndürüyoruz.
            return items_arr;
        }

    }
****/

import java.lang.reflect.Array;
import java.util.*;
import java.util.LinkedList;
import java.util.Queue;
import com.example.otelrezervasyonsistemi.FoodListItem;

public class FoodList {
    private Queue<FoodListItem> items; // Yiyecek listesi için bir kuyruk
    private ArrayList<FoodListItem> items_arr= new ArrayList<>();

    public FoodList() {
        this.items = new LinkedList<>(); // Kuyruğu bir LinkedList ile başlatıyoruz.


        ArrayList<String> breakfast_arr = new ArrayList<>();
        ArrayList<String> lunch_arr = new ArrayList<>();
        ArrayList<String> dinner_arr = new ArrayList<>();

        breakfast_arr.add("omlet");
        breakfast_arr.add("tea");
        breakfast_arr.add("menemen");

        lunch_arr.add("salata");
        lunch_arr.add("çorba");
        lunch_arr.add("meze");

        dinner_arr.add("balık");
        dinner_arr.add("kebap");
        dinner_arr.add("tavuk");


        // Varsayılan yiyecek öğelerini ekliyoruz.
        items.add(new FoodListItem("Breakfast", 90.0, breakfast_arr));
        items.add(new FoodListItem("Lunch", 120.0, lunch_arr));
        items.add(new FoodListItem("Dinner", 130.0, dinner_arr));
    }

    // Yiyecek eklemek için bir metot
    public void addItem(FoodListItem item) {
        items.add(item); // Yiyecekleri kuyruğa ekliyoruz.
    }

    // Tüm yiyecekleri almak için bir metot
    public ArrayList<FoodListItem> getItems() {
        while (!items.isEmpty()) {
            items_arr.add(items.poll());
        }
        return items_arr;
    }
}
