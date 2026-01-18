package org.example.PricingEngineSubscription.service;

import org.example.PricingEngineSubscription.domain.Alert;
import org.example.PricingEngineSubscription.domain.PriceTick;
import org.example.PricingEngineSubscription.repository.AlertRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class AlertService {

    private static final Logger log = LoggerFactory.getLogger(AlertService.class);

    private final AlertRepository repository;
    private final AlertEvaluator evaluator;

    public AlertService(AlertRepository repository, AlertEvaluator evaluator) {
        this.repository = repository;
        this.evaluator = evaluator;
    }

    public Alert createAlert(Alert alert) {
        repository.save(alert);

        log.info(
                "Alert created: id={} symbol={} condition={} threshold={}",
                alert.getId(),
                alert.getSymbol(),
                alert.getCondition(),
                alert.getThreshold()
        );

        return alert;
    }

    public Collection<Alert> getAllAlerts() {
        return repository.findAll();
    }

    public boolean deleteAlert(String id) {
        boolean deleted = repository.deleteById(id);

        if (deleted) {
            log.info("Alert deleted: id={}", id);
        }

        return deleted;
    }

    /**
     * Called for every incoming market price tick.
     */
    public void onPriceTick(PriceTick tick) {
        for (Alert alert : repository.findAll()) {
            evaluator.evaluate(alert, tick);
        }
    }
}
