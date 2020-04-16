package com.aoher.util;

import java.util.Arrays;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class SoapRequestBuilder {

    private static final String CUSTOMER_START = "<ns2:customer>";
    private static final String CUSTOMER_END = "</ns2:customer>";

    private static final String FIRST_NAME_START = "<ns2:firstName>";
    private static final String FIRST_NAME_END = "</ns2:firstName>";

    private static final String LAST_NAME_START = "<ns2:lastName>";
    private static final String LAST_NAME_END = "</ns2:lastName>";

    private static final String PRODUCT_START = "<ns2:product>";
    private static final String PRODUCT_END = "</ns2:product>";

    private static final String ID_START = "<ns2:id>";
    private static final String ID_END = "</ns2:id>";

    private static final String NAME_START = "<ns2:name>";
    private static final String NAME_END = "</ns2:name>";

    private static final String LINE_ITEM_START = "<ns2:lineItem>";
    private static final String LINE_ITEM_END = "</ns2:lineItem>";

    private static final String QUANTITY_START = "<ns2:quantity>";
    private static final String QUANTITY_END = "</ns2:quantity>";

    private static final String LINE_ITEMS_START = "<ns2:lineItems>";
    private static final String LINE_ITEMS_END = "</ns2:lineItems>";

    private static final String ORDER_START = "<ns2:order xmlns:ns2=\"%s\">";
    private static final String ORDER_END = "</ns2:order>";

    private static final String ORDER_CONFIRMATION_START = "<ns2:orderConfirmation xmlns:ns2=\"%s\">";
    private static final String ORDER_CONFIRMATION_END = "</ns2:orderConfirmation>";

    private static final String CONFIRMATION_ID_START = "<ns2:confirmationId>";
    private static final String CONFIRMATION_ID_END = "</ns2:confirmationId>";

    public static String getCustomer(String firstName, String lastName) {
        return CUSTOMER_START +
                FIRST_NAME_START + firstName + FIRST_NAME_END +
                LAST_NAME_START + lastName + LAST_NAME_END +
                CUSTOMER_END;
    }

    public static String getProduct(String id, String name) {
        return PRODUCT_START +
                ID_START + id + ID_END +
                NAME_START + name + NAME_END +
                PRODUCT_END;
    }

    public static String getLineItem(String... products) {
        return Arrays.stream(products)
                .collect(Collectors.joining(
                        "",
                        LINE_ITEM_START,
                        QUANTITY_START + products.length + QUANTITY_END + LINE_ITEM_END)
                );
    }

    public static String getLineItems(String... items) {
        return Arrays.stream(items).collect(Collectors.joining("", LINE_ITEMS_START, LINE_ITEMS_END));
    }

    public static String getOrder(String url, String customer, String lineItems) {
        return format(ORDER_START, url) + customer + lineItems + ORDER_END;
    }

    public static String getOrderConfirmation(String url, String id) {
        return format(ORDER_CONFIRMATION_START, url) +
                CONFIRMATION_ID_START + id + CONFIRMATION_ID_END +
                ORDER_CONFIRMATION_END;
    }

    private SoapRequestBuilder() {
    }
}
