package org.example.PricingEngineSubscription.repository;

import org.example.PricingEngineSubscription.domain.Alert;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AlertRepository {

    private final Map<String, Alert> alerts = new ConcurrentHashMap<>();

    public Alert save(Alert alert) {
        alerts.put(alert.getId(), alert);
        return alert;
    }

    public Alert findById(String id) {
        return alerts.get(id);
    }

    public Collection<Alert> findAll() {
        return alerts.values();
    }

    public boolean deleteById(String id) {
        return alerts.remove(id) != null;
    }

    public void clear() {
        alerts.clear();
    }
}
