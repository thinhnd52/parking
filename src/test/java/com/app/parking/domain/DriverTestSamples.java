package com.app.parking.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DriverTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Driver getDriverSample1() {
        return new Driver().id(1L).lastName("lastName1").firstName("firstName1").phone("phone1").licenseNo("licenseNo1");
    }

    public static Driver getDriverSample2() {
        return new Driver().id(2L).lastName("lastName2").firstName("firstName2").phone("phone2").licenseNo("licenseNo2");
    }

    public static Driver getDriverRandomSampleGenerator() {
        return new Driver()
            .id(longCount.incrementAndGet())
            .lastName(UUID.randomUUID().toString())
            .firstName(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString())
            .licenseNo(UUID.randomUUID().toString());
    }
}
