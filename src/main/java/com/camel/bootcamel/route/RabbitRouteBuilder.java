package com.camel.bootcamel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty("app.feature.rabbit-route-enabled")
public class RabbitRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
//        write to rabbit exchange named myExchange
        from("timer:myTimer?period=10000")
                .id("timer-to-rabbit-writer")
                .process(exchange -> exchange.getIn().setBody("recurrent hello message"))
                .to("spring-rabbitmq:myExchange?routingKey=myRoutingKey")
                .log("Sending new message: ${body}");

//        read from rabbit myExchange
        from("spring-rabbitmq:myExchange?queues=myQueue&routingKey=myRoutingKey")
                .id("rabbit-reader")
                .log("New message received: ${body}");
    }
}
