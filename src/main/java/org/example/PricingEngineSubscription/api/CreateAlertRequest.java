package org.example.PricingEngineSubscription.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.example.PricingEngineSubscription.domain.AlertCondition;

public class CreateAlertRequest {

    @NotBlank(message = "Symbol must not be blank")
    private String symbol;

    @NotNull(message = "Condition must be specified (ABOVE or BELOW)")
    private AlertCondition condition;

    @Positive(message = "Threshold must be a positive number")
    private double threshold;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public AlertCondition getCondition() {
        return condition;
    }

    public void setCondition(AlertCondition condition) {
        this.condition = condition;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }
}
