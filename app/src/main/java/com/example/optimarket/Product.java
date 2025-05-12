package com.example.optimarket;

public abstract class Product {
    //Bu değişkenler veritabanında tutulan değişkenler
    private String name;
    private double price;
    private double cost;
    private double profit;
    private double discountAmount;

    public void setName(String name){this.name=name;}
    public String getName(){return name;}

    public void setPrice(double price){this.price=price;}
    public double getPrice(){return price;}

    public void setCost(double cost){this.cost=cost;}
    public double getCost(){return cost;}

    public void setProfit(double profit){this.profit=profit;}
    public double getProfit(){return profit;}

    public void setDiscountAmount(double discountAmount){this.discountAmount=discountAmount;}
    public double getDiscountAmount(){return discountAmount;}

    public abstract String getCategory();
}
