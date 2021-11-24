package com.camel.bootcamel.service;

import org.apache.camel.Exchange;

public interface OrderService {
    void handleOrder(Exchange exchange);

    void buildCombinedResponse(Exchange exchange);
}
