package org.example.PricingEngineSubscription.api;

import org.example.PricingEngineSubscription.domain.Alert;
import org.example.PricingEngineSubscription.service.AlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @PostMapping
    public ResponseEntity<AlertResponse> createAlert(
            @jakarta.validation.Valid @RequestBody CreateAlertRequest request
    )
    {
        Alert alert = new Alert(
                request.getSymbol(),
                request.getCondition(),
                request.getThreshold()
        );

        Alert created = alertService.createAlert(alert);

        return ResponseEntity.ok(AlertResponse.from(created));
    }

    @GetMapping
    public List<AlertResponse> getAllAlerts() {
        return alertService.getAllAlerts()
                .stream()
                .map(AlertResponse::from)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlert(@PathVariable String id) {
        boolean deleted = alertService.deleteAlert(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
