package com.example.otelrezervasyonsistemi;

import java.util.LinkedList;

public class SportServicesList {
    private LinkedList<String> services; // Spor servisleri için bir linked list

    public SportServicesList() {
        services = new LinkedList<>();

        // Varsayılan spor servislerini ekliyoruz.
        services.add("Yoga");
        services.add("Pilates");
        services.add("Swimming");
        services.add("Tennis");
        services.add("Fitness");
    }

    // Spor servisi eklemek için bir metot
    public void addService(String service) {
        services.add(service);
    }

    // Tüm spor servislerini almak için bir metot
    public LinkedList<String> getServices() {
        return services;
    }
}

