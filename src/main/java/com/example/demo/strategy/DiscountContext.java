package com.example.demo.strategy;

import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class DiscountContext {

    private final Map<String, DiscountStrategy> strategies;

    public DiscountContext(Map<String, DiscountStrategy> strategies) {
        this.strategies = strategies;
    }

    public double applyDiscount(double price, String discountType) {
        if (discountType == null) {
            return price;
        }
        DiscountStrategy strategy = strategies.getOrDefault(discountType.toUpperCase(), strategies.get("NONE"));
        if (strategy == null) {
            return price;
        }
        return strategy.calculateDiscount(price);
    }
}
