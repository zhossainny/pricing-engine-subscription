package org.example.PricingEngineSubscription.service;

import org.example.PricingEngineSubscription.domain.Alert;
import org.example.PricingEngineSubscription.domain.AlertCondition;
import org.example.PricingEngineSubscription.domain.PriceTick;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlertEvaluator {

    private static final Logger log = LoggerFactory.getLogger(AlertEvaluator.class);

    /**
     * Evaluates a single alert against a price tick.
     * Triggers the alert at most once.
     */
    public void evaluate(Alert alert, PriceTick tick) {

        // Ignore if already triggered
        if (alert.isTriggered()) {
            return;
        }

        // Symbol mismatch: ignore
        if (!alert.getSymbol().equalsIgnoreCase(tick.symbol())) {
            return;
        }

        boolean shouldTrigger = switch (alert.getCondition()) {
            case ABOVE -> tick.price() > alert.getThreshold();
            case BELOW -> tick.price() < alert.getThreshold();
        };

        if (shouldTrigger) {
            alert.markTriggered();

            log.info(
                    "ALERT TRIGGERED: symbol={} condition={} threshold={} price={}",
                    alert.getSymbol(),
                    alert.getCondition(),
                    alert.getThreshold(),
                    tick.price()
            );
        }
    }
}
