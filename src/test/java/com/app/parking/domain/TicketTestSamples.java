package com.app.parking.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TicketTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Ticket getTicketSample1() {
        return new Ticket()
            .id(1L)
            .ticketNo("ticketNo1")
            .lastName("lastName1")
            .firstName("firstName1")
            .phone("phone1")
            .room("room1")
            .licensePlate("licensePlate1")
            .note("note1");
    }

    public static Ticket getTicketSample2() {
        return new Ticket()
            .id(2L)
            .ticketNo("ticketNo2")
            .lastName("lastName2")
            .firstName("firstName2")
            .phone("phone2")
            .room("room2")
            .licensePlate("licensePlate2")
            .note("note2");
    }

    public static Ticket getTicketRandomSampleGenerator() {
        return new Ticket()
            .id(longCount.incrementAndGet())
            .ticketNo(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .firstName(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString())
            .room(UUID.randomUUID().toString())
            .licensePlate(UUID.randomUUID().toString())
            .note(UUID.randomUUID().toString());
    }
}
