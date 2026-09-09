package com.example.demo.strategy;

import org.springframework.stereotype.Component;

@Component("SEASONAL")
public class SeasonalSaleStrategy implements DiscountStrategy {

    @Override
    public double calculateDiscount(double price) {
        return price * 0.80;
    }
}
