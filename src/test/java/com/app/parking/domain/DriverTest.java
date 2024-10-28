package com.app.parking.domain;

import static com.app.parking.domain.DriverTestSamples.*;
import static com.app.parking.domain.TicketTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.app.parking.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DriverTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Driver.class);
        Driver driver1 = getDriverSample1();
        Driver driver2 = new Driver();
        assertThat(driver1).isNotEqualTo(driver2);

        driver2.setId(driver1.getId());
        assertThat(driver1).isEqualTo(driver2);

        driver2 = getDriverSample2();
        assertThat(driver1).isNotEqualTo(driver2);
    }

    @Test
    void ticketTest() {
        Driver driver = getDriverRandomSampleGenerator();
        Ticket ticketBack = getTicketRandomSampleGenerator();

        driver.setTicket(ticketBack);
        assertThat(driver.getTicket()).isEqualTo(ticketBack);
        assertThat(ticketBack.getDriver()).isEqualTo(driver);

        driver.ticket(null);
        assertThat(driver.getTicket()).isNull();
        assertThat(ticketBack.getDriver()).isNull();
    }
}
