package com.camel.bootcamel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class RabbitRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:myTimer?period=1000")
                .to("direct:regular-logger");

        from("direct:regular-logger")
                .log("let's log");
    }
}
