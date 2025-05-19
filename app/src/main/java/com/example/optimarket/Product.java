package com.example.optimarket;

public abstract class Product {
    //Bu değişkenler veritabanında tutulan değişkenler
    private String name;
    private double price;
    private double cost;
    private double profit;
    private int stock;
    private double discountAmount;

    public void setName(String name){this.name=name;}
    public String getName(){return name;}

    public void setPrice(double price){this.price=price;}
    public double getPrice(){return price;}

    public void setCost(double cost){this.cost=cost;}
    public double getCost(){return cost;}

    public void calculateProfit() {this.profit = this.price - this.cost;}
    public double getProfit(){return profit;}

    public void setStock(int stock){this.stock=stock;}
    public int getStock(){return stock;}

    public void setDiscountAmount(double discountAmount){
        this.discountAmount=discountAmount;
        this.price -= discountAmount;
        calculateProfit();}
    public double getDiscountAmount(){return discountAmount;}


    public abstract String getCategory();
}
