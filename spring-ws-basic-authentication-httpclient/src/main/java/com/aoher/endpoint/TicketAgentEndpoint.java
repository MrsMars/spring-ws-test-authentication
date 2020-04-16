package com.aoher.endpoint;

import com.aoher.ticketagent.ObjectFactory;
import com.aoher.ticketagent.TFlightsResponse;
import com.aoher.ticketagent.TListFlights;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.bind.JAXBElement;
import java.math.BigInteger;

@Endpoint
public class TicketAgentEndpoint {

    private static final String NAMESPACE = "http://aoher.com/TicketAgent.xsd";

    @PayloadRoot(namespace = NAMESPACE, localPart = "listFlightsRequest")
    @ResponsePayload
    public JAXBElement<TFlightsResponse> listFlights(@RequestPayload JAXBElement<TListFlights> request) {
        ObjectFactory factory = new ObjectFactory();
        TFlightsResponse response = factory.createTFlightsResponse();
        response.getFlightNumber().add(BigInteger.valueOf(101));
        return factory.createListFlightsResponse(response);
    }
}
