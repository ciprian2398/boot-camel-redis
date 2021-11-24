package com.camel.bootcamel.service.impl;

import com.camel.bootcamel.service.OrderService;
import lombok.extern.java.Log;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Service;

@Log
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Override
    public void handleOrder(Exchange exchange) {
        log.info("handleOrder " + exchange.getIn().getBody());
    }

    @Override
    public void buildCombinedResponse(Exchange exchange) {
        log.info("buildCombinedResponse " + exchange.getIn().getBody());
    }
}
