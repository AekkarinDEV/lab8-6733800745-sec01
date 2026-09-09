package com.example.demo.strategy;

import org.springframework.stereotype.Component;

@Component("NONE")
public class NoDiscountStrategy implements DiscountStrategy {

    @Override
    public double calculateDiscount(double price) {
        return price;
    }
}
