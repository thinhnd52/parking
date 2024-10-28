package com.app.parking.domain;

import static com.app.parking.domain.DriverTestSamples.*;
import static com.app.parking.domain.SpotTestSamples.*;
import static com.app.parking.domain.TicketTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.app.parking.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TicketTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ticket.class);
        Ticket ticket1 = getTicketSample1();
        Ticket ticket2 = new Ticket();
        assertThat(ticket1).isNotEqualTo(ticket2);

        ticket2.setId(ticket1.getId());
        assertThat(ticket1).isEqualTo(ticket2);

        ticket2 = getTicketSample2();
        assertThat(ticket1).isNotEqualTo(ticket2);
    }

    @Test
    void spotTest() {
        Ticket ticket = getTicketRandomSampleGenerator();
        Spot spotBack = getSpotRandomSampleGenerator();

        ticket.setSpot(spotBack);
        assertThat(ticket.getSpot()).isEqualTo(spotBack);

        ticket.spot(null);
        assertThat(ticket.getSpot()).isNull();
    }

    @Test
    void driverTest() {
        Ticket ticket = getTicketRandomSampleGenerator();
        Driver driverBack = getDriverRandomSampleGenerator();

        ticket.setDriver(driverBack);
        assertThat(ticket.getDriver()).isEqualTo(driverBack);

        ticket.driver(null);
        assertThat(ticket.getDriver()).isNull();
    }
}
