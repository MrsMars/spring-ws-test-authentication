package com.aoher.endpoint;

import com.aoher.types.order.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateOrderEndpointTest {

    private static CreateOrderEndpoint createOrderEndpoint;
    private Order order;

    @BeforeClass
    public static void setUpClass() {
        createOrderEndpoint = new CreateOrderEndpoint();
    }

    @Before
    public void setUp() {
        ObjectFactory factory = new ObjectFactory();

        CustomerType customer = factory.createCustomerType();
        customer.setFirstName("John");
        customer.setLastName("Doe");

        ProductType product = factory.createProductType();
        product.setId("1");
        product.setName("batman action figure");

        LineItemType lineItem = factory.createLineItemType();
        lineItem.setProduct(product);
        lineItem.setQuantity(BigInteger.valueOf(1));

        LineItemsType lineItems = factory.createLineItemsType();
        lineItems.getLineItem().add(lineItem);

        order = factory.createOrder();
        order.setCustomer(customer);
        order.setLineItems(lineItems);
    }

    @Test
    public void testCreateOrder() {
        assertThat(createOrderEndpoint.createOrder(order).getConfirmationId()).isNotBlank();
    }
}