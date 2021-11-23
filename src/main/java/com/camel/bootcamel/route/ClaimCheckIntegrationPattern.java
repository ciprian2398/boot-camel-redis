package com.camel.bootcamel.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ClaimCheckOperation;
import org.springframework.stereotype.Component;

@Component
public class ClaimCheckIntegrationPattern extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:myTimer?period=10000")
                .threads(2)
                .id("timer-writer")
                .process(exchange -> exchange.getIn().setBody("recurrent hello message"))
                .log("AAA1: ${body}")
                .claimCheck(ClaimCheckOperation.Push)
                .transform().constant("Bye World")
                .log("AAA2: ${body}")
                .claimCheck(ClaimCheckOperation.Pop)
                .log("AAA3: ${body}");
    }
}
