package org.example.PricingEngineSubscription.market;

import org.example.PricingEngineSubscription.domain.PriceTick;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class SyntheticMarketData {

    private final ConcurrentHashMap<String, Double> prices;
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    private final ScheduledExecutorService executor =
            Executors.newSingleThreadScheduledExecutor();

    public SyntheticMarketData(Map<String, Double> initialPrices) {
        this.prices = new ConcurrentHashMap<>(initialPrices);
    }

    public PriceTick generateTick(String symbol) {
        double current = prices.get(symbol);

        // Random walk: -0.5% to +0.5%
        double changePct = random.nextDouble(-0.005, 0.005);

        double newPrice = BigDecimal
                .valueOf(current * (1 + changePct))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        prices.put(symbol, newPrice);

        return new PriceTick(symbol, newPrice);
    }

    public void stream(Consumer<PriceTick> onTick, long intervalMs) {
        executor.scheduleAtFixedRate(() -> {
            prices.keySet().forEach(symbol ->
                    onTick.accept(generateTick(symbol))
            );
        }, 0, intervalMs, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        executor.shutdownNow();
    }
}
