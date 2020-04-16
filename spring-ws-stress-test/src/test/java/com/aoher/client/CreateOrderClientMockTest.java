package com.aoher.client;

import com.aoher.types.order.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Source;
import java.math.BigInteger;

import static com.aoher.util.SoapRequestBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.ws.test.client.RequestMatchers.payload;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateOrderClientMockTest {

    private static final String NAMESPACE = "http://aoher.com/types/order";

    @Autowired
    private CreateOrderClient createOrderClient;

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    private MockWebServiceServer mockWebServiceServer;

    private CustomerType customer;
    private ProductType product;
    private LineItemsType lineItems;

    @Before
    public void setUp() {
        mockWebServiceServer = MockWebServiceServer.createServer(webServiceTemplate);

        ObjectFactory factory = new ObjectFactory();

        customer = factory.createCustomerType();
        customer.setFirstName("Jane");
        customer.setLastName("Doe");

        product = factory.createProductType();
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
        String id = "5678";

        String customerStr = getCustomer(customer.getFirstName(), customer.getLastName());
        String productStr = getProduct(product.getId(), product.getName());
        String lineItemStr = getLineItem(productStr);
        String lineItemsStr = getLineItems(lineItemStr);
        String order = getOrder(NAMESPACE, customerStr, lineItemsStr);

        Source requestPayload = new StringSource(order);

        String orderConfirmation = getOrderConfirmation(NAMESPACE, id);
        Source responsePayload = new StringSource(orderConfirmation);

        mockWebServiceServer.expect(payload(requestPayload)).andRespond(withPayload(responsePayload));

        assertThat(createOrderClient.createOrder(customer, lineItems).getConfirmationId()).isEqualTo(id);
        mockWebServiceServer.verify();
    }
}
