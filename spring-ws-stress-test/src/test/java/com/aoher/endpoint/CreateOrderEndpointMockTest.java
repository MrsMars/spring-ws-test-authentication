package com.aoher.endpoint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.ws.test.server.ResponseMatchers;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Source;
import java.util.Collections;
import java.util.Map;

import static com.aoher.util.SoapRequestBuilder.*;
import static org.springframework.ws.test.server.RequestCreators.withPayload;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateOrderEndpointMockTest {

    private static final String NAMESPACE = "http://aoher.com/types/order";

    @Autowired
    private ApplicationContext applicationContext;

    private MockWebServiceClient mockClient;

    @Before
    public void setUp() {
        mockClient = MockWebServiceClient.createClient(applicationContext);
    }

    @Test
    public void testCreateOrder() {
        String customer = getCustomer("John", "Doe");
        String product = getProduct("2", "batman action figure");
        String lineItem = getLineItem(product);
        String lineItems = getLineItems(lineItem);
        String order = getOrder(NAMESPACE, customer, lineItems);

        Source requestPayload = new StringSource(order);

        Map<String, String> namespaces = Collections.singletonMap("ns1", NAMESPACE);

        mockClient.sendRequest(withPayload(requestPayload))
                .andExpect(ResponseMatchers
                        .xpath("/ns1:orderConfirmation/ns1:confirmationId", namespaces)
                        .exists());
    }
}
