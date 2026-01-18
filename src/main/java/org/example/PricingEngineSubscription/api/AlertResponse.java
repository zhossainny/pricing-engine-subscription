package org.example.PricingEngineSubscription.api;

import org.example.PricingEngineSubscription.domain.Alert;
import org.example.PricingEngineSubscription.domain.AlertCondition;

public class AlertResponse {

    private String id;
    private String symbol;
    private AlertCondition condition;
    private double threshold;
    private boolean triggered;

    public static AlertResponse from(Alert alert) {
        AlertResponse response = new AlertResponse();
        response.id = alert.getId();
        response.symbol = alert.getSymbol();
        response.condition = alert.getCondition();
        response.threshold = alert.getThreshold();
        response.triggered = alert.isTriggered();
        return response;
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
}
