package com.aoher.client;

import com.aoher.types.order.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateOrderClientTest {

    @InjectMocks
    private CreateOrderClient createOrderClient;

    @Mock
    private WebServiceTemplate webServiceTemplate;

    @Mock
    private OrderConfirmation orderConfirmation;

    private CustomerType customer;
    private LineItemsType lineItems;

    @Before
    public void setUp() {
        ObjectFactory factory = new ObjectFactory();

        customer = factory.createCustomerType();
        customer.setFirstName("Jane");
        customer.setLastName("Doe");

        ProductType product = factory.createProductType();
        product.setId("2");
        product.setName("superman action figure");

        LineItemType lineItem = factory.createLineItemType();
        lineItem.setProduct(product);
        lineItem.setQuantity(BigInteger.valueOf(1));

        lineItems = factory.createLineItemsType();
        lineItems.getLineItem().add(lineItem);
    }

    @Test
    public void testCreateOrder() {
        String id = "1234";

        when(webServiceTemplate.marshalSendAndReceive(any())).thenReturn(orderConfirmation);
        when(orderConfirmation.getConfirmationId()).thenReturn(id);

        assertThat(createOrderClient.createOrder(customer, lineItems).getConfirmationId()).isEqualTo(id);
    }
}