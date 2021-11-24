package com.camel.bootcamel.route;

import com.camel.bootcamel.strategy.OrderStrategy;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!exclusive-config")
public class ComposedMessageProcessorRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:myTimer?period=5000")
                .id("ComposedMessage-timer-writer")
                .process(exchange -> exchange.getIn().setBody("order1@order2@order3"))
                .split(body().tokenize("@"), new OrderStrategy())
                    .to("bean:orderService?method=handleOrder")
                .end()
                .to("bean:orderService?method=buildCombinedResponse");
    }
}
