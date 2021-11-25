package com.camel.bootcamel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@Component
@ConditionalOnProperty("app.feature.dead-letter-route-enabled")
public class RabbitCamelDeadLetterRouteBuilder extends RouteBuilder {

    private final DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

    @Override
    public void configure() throws Exception {

        errorHandler(
                deadLetterChannel("log:mesajisdead?level=ERROR")//todo re enqueue
                        .maximumRedeliveries(3)
                        .redeliveryDelay(5000)
        );

        from("timer:myTimer?period=10000")
                .id("timer-to-rabbit-dead-letter")
                .process(exchange -> exchange.getIn().setBody(exchange.getIn().getHeader("firedTime", String.class)))
                .to("spring-rabbitmq:myDeadLetterExchange?routingKey=myDeadRoutingKey")
                .log("Sending new message: ${body}");

        from("spring-rabbitmq:myDeadLetterExchange?queues=myDeadQueue&routingKey=myDeadRoutingKey")
                .id("rabbit-reader")
                .process(exchange -> {
                    long timpestamp = dateFormat.parse(exchange.getIn().getBody(String.class)).getTime();

                    if (new Date(timpestamp).getSeconds() % 2 == 0) {
                        throw new Exception("validation exxception");
                    }
                })
                .log("New message received: ${body}");
    }
}
