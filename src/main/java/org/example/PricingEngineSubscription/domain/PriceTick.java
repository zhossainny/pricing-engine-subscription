package org.example.PricingEngineSubscription.domain;

public record PriceTick(
        String symbol,
        double price,
        long timestamp
) {
    public PriceTick(String symbol, double price) {
        this(symbol, price, System.currentTimeMillis());
    }
}