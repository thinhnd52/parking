package com.app.parking.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SpotTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Spot getSpotSample1() {
        return new Spot().id(1L).name("name1").spotNo("spotNo1").level(1).spotRow(1).spotColumn(1);
    }

    public static Spot getSpotSample2() {
        return new Spot().id(2L).name("name2").spotNo("spotNo2").level(2).spotRow(2).spotColumn(2);
    }

    public static Spot getSpotRandomSampleGenerator() {
        return new Spot()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .spotNo(UUID.randomUUID().toString())
            .level(intCount.incrementAndGet())
            .spotRow(intCount.incrementAndGet())
            .spotColumn(intCount.incrementAndGet());
    }
}
