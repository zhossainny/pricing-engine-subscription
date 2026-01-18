package org.example.PricingEngineSubscription.service;

import org.example.PricingEngineSubscription.domain.Alert;
import org.example.PricingEngineSubscription.domain.AlertCondition;
import org.example.PricingEngineSubscription.domain.PriceTick;
import org.example.PricingEngineSubscription.repository.AlertRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlertServiceTest {

    private AlertService alertService;

    @BeforeEach
    void setUp() {
        AlertRepository repository = new AlertRepository();
        AlertEvaluator evaluator = new AlertEvaluator();
        alertService = new AlertService(repository, evaluator);
    }

    @Test
    void shouldCreateAndStoreAlert() {
        Alert alert = new Alert("AAPL", AlertCondition.ABOVE, 200.0);

        alertService.createAlert(alert);

        assertEquals(1, alertService.getAllAlerts().size());
    }

    @Test
    void shouldTriggerAlertViaServiceOnPriceTick() {
        Alert alert = new Alert("AAPL", AlertCondition.ABOVE, 180.0);
        alertService.createAlert(alert);

        alertService.onPriceTick(new PriceTick("AAPL", 185.0));

        assertTrue(alert.isTriggered());
    }

    @Test
    void shouldDeleteAlert() {
        Alert alert = new Alert("AAPL", AlertCondition.ABOVE, 200.0);
        alertService.createAlert(alert);

        boolean deleted = alertService.deleteAlert(alert.getId());

        assertTrue(deleted);
        assertEquals(0, alertService.getAllAlerts().size());
    }
}
