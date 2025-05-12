package com.example.optimarket;

public class DiscountedProductFilter implements ProductFilter{
    public boolean filter(Product product){
        return product.getDiscountAmount() > 0;
    }
}
