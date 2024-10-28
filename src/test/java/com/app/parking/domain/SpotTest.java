package com.app.parking.domain;

import static com.app.parking.domain.SpotTestSamples.*;
import static com.app.parking.domain.TicketTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.app.parking.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SpotTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Spot.class);
        Spot spot1 = getSpotSample1();
        Spot spot2 = new Spot();
        assertThat(spot1).isNotEqualTo(spot2);

        spot2.setId(spot1.getId());
        assertThat(spot1).isEqualTo(spot2);

        spot2 = getSpotSample2();
        assertThat(spot1).isNotEqualTo(spot2);
    }

    @Test
    void ticketTest() {
        Spot spot = getSpotRandomSampleGenerator();
        Ticket ticketBack = getTicketRandomSampleGenerator();

        spot.setTicket(ticketBack);
        assertThat(spot.getTicket()).isEqualTo(ticketBack);
        assertThat(ticketBack.getSpot()).isEqualTo(spot);

        spot.ticket(null);
        assertThat(spot.getTicket()).isNull();
        assertThat(ticketBack.getSpot()).isNull();
    }
}
