package com.example.optimarket;

public class Snacks extends Product {

    // Parametresiz constructor
    public Snacks() {
        super();
    }

    // İsim ve fiyat ile constructor
    public Snacks(String name, double price) {
        super(name, price);
    }

    // Tam parametreli constructor
    public Snacks(String name, double price, double cost, int stock) {
        super(name, price, cost, stock);
    }

    @Override
    public String getCategory() {
        return "Snacks";
    }
}