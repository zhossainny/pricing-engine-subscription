package org.example.PricingEngineSubscription.config;

import org.example.PricingEngineSubscription.repository.AlertRepository;
import org.example.PricingEngineSubscription.service.AlertEvaluator;
import org.example.PricingEngineSubscription.service.AlertService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public AlertRepository alertRepository() {
        return new AlertRepository();
    }

    @Bean
    public AlertEvaluator alertEvaluator() {
        return new AlertEvaluator();
    }

    @Bean
    public AlertService alertService(
            AlertRepository alertRepository,
            AlertEvaluator alertEvaluator
    ) {
        return new AlertService(alertRepository, alertEvaluator);
    }
}
