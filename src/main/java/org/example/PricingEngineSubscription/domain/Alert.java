package org.example.PricingEngineSubscription.domain;

import java.util.UUID;

public class Alert {

    private final String id;
    private final String symbol;
    private final AlertCondition condition;
    private final double threshold;

    // Tracks whether the alert has already fired
    private volatile boolean triggered;

    public Alert(String symbol, AlertCondition condition, double threshold) {
        this.id = UUID.randomUUID().toString();
        this.symbol = symbol;
        this.condition = condition;
        this.threshold = threshold;
        this.triggered = false;
    }

    public String getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public AlertCondition getCondition() {
        return condition;
    }

    public double getThreshold() {
        return threshold;
    }

    public boolean isTriggered() {
        return triggered;
    }

    public void markTriggered() {
        this.triggered = true;
    }
}
