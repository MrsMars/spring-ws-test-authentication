package com.aoher.endpoint;

import com.aoher.types.order.ObjectFactory;
import com.aoher.types.order.Order;
import com.aoher.types.order.OrderConfirmation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.UUID;

@Endpoint
public class CreateOrderEndpoint {

    private static final Logger log = LoggerFactory.getLogger(CreateOrderEndpoint.class);

    private static final String NAMESPACE_URI = "http://aoher.com/types/order";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "order")
    @ResponsePayload
    public OrderConfirmation createOrder(@RequestPayload Order request) {
        log.info("Endpoint received order for Customer[firstName={},lastName={}]",
                request.getCustomer().getFirstName(),
                request.getCustomer().getLastName()
        );

        ObjectFactory factory = new ObjectFactory();
        OrderConfirmation response = factory.createOrderConfirmation();
        response.setConfirmationId(UUID.randomUUID().toString());

        log.info("Endpoint sending orderConfirmationId='{}'", response.getConfirmationId());
        return response;
    }
}
