package org.example.PricingEngineSubscription.service;

import org.example.PricingEngineSubscription.domain.Alert;
import org.example.PricingEngineSubscription.domain.AlertCondition;
import org.example.PricingEngineSubscription.domain.PriceTick;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlertEvaluatorTest {

    private final AlertEvaluator evaluator = new AlertEvaluator();

    @Test
    void shouldTriggerAlertWhenPriceCrossesAboveThreshold() {
        Alert alert = new Alert("AAPL", AlertCondition.ABOVE, 190.0);
        PriceTick tick = new PriceTick("AAPL", 191.0);

        evaluator.evaluate(alert, tick);

        assertTrue(alert.isTriggered(), "Alert should be triggered when price is above threshold");
    }

    @Test
    void shouldTriggerAlertWhenPriceCrossesBelowThreshold() {
        Alert alert = new Alert("TSLA", AlertCondition.BELOW, 150.0);
        PriceTick tick = new PriceTick("TSLA", 149.0);

        evaluator.evaluate(alert, tick);

        assertTrue(alert.isTriggered(), "Alert should be triggered when price is below threshold");
    }

    @Test
    void shouldNotTriggerAlertForDifferentSymbol() {
        Alert alert = new Alert("AAPL", AlertCondition.ABOVE, 190.0);
        PriceTick tick = new PriceTick("MSFT", 300.0);

        evaluator.evaluate(alert, tick);

        assertFalse(alert.isTriggered(), "Alert should not trigger for different symbol");
    }

    @Test
    void shouldTriggerOnlyOnce() {
        Alert alert = new Alert("AAPL", AlertCondition.ABOVE, 170.0);

        evaluator.evaluate(alert, new PriceTick("AAPL", 180.0));
        evaluator.evaluate(alert, new PriceTick("AAPL", 185.0));
        evaluator.evaluate(alert, new PriceTick("AAPL", 190.0));

        assertTrue(alert.isTriggered(), "Alert should be triggered");
    }
}
