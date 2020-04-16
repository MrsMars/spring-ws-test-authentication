package com.aoher;

import com.aoher.client.TicketAgentClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AppTest {

    @Autowired
    private TicketAgentClient ticketAgentClient;

    @Test
    public void testListFlights() {
        List<BigInteger> flights = ticketAgentClient.listFlights();

        assertThat(flights.get(0)).isEqualTo(BigInteger.valueOf(101));
    }
}