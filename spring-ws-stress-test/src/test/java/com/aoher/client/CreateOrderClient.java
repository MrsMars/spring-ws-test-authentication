package com.aoher.client;

import com.aoher.types.order.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CreateOrderClient {

    @Autowired
    private CreateOrderClient createOrderClient;

    private CustomerType customer;
    private LineItemsType lineItems;

    @Before
    public void setUp() {
        ObjectFactory factory = new ObjectFactory();

        customer = factory.createCustomerType();
        customer.setFirstName("John");
        customer.setLastName("Doe");

        ProductType product1 = factory.createProductType();
        product1.setId("1");

        LineItemType lineItem1 = factory.createLineItemType();
        lineItem1.setProduct(product1);
        lineItem1.setQuantity(BigInteger.valueOf(2));

        lineItems = factory.createLineItemsType();
        lineItems.getLineItem().add(lineItem1);
    }

    @Test
    public void testCreateOrder() {
        assertThat(createOrderClient.createOrder(customer, lineItems)
                .getConfirmationId()).isEqualTo("gero et");
    }
}
