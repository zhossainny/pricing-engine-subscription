package org.example.PricingEngineSubscription.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.example.PricingEngineSubscription.domain.PriceTick;
import org.example.PricingEngineSubscription.market.SyntheticMarketData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MarketDataService {

    private static final Logger log = LoggerFactory.getLogger(MarketDataService.class);

    private final AlertService alertService;
    private SyntheticMarketData marketData;

    public MarketDataService(AlertService alertService) {
        this.alertService = alertService;
    }

    @PostConstruct
    public void start() {
        log.info("Starting synthetic market data stream");

        marketData = new SyntheticMarketData(
                Map.of(
                        "AAPL", 185.00,
                        "GOOGL", 140.00,
                        "MSFT", 420.00,
                        "TSLA", 175.00
                )
        );

        marketData.stream(this::onTick, 500);
    }

    private void onTick(PriceTick tick) {
        // Show real-time streaming prices
//        System.out.printf(
//                "TICK [%s] price=%.2f%n",
//                tick.symbol(),
//                tick.price()
//        );
        alertService.onPriceTick(tick);
    }

    @PreDestroy
    public void stop() {
        log.info("Stopping synthetic market data stream");

        if (marketData != null) {
            marketData.shutdown();
        }
    }
}
