package com.camel.bootcamel.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static io.netty.handler.codec.http.HttpMethod.GET;

@Component
@ConditionalOnProperty("app.feature.http-enricher-route-enabled")
public class HttpEnricherRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:myTimer?period=5000")
                .id("MyAggregationStrategy-timer-writer")
                .process(exchange -> exchange.getIn().setBody("excelent"))
                .setHeader(Exchange.HTTP_METHOD,  constant(GET))
                .setHeader(Exchange.HTTP_QUERY, constant("bibkeys=ISBN:0201558025,LCCN:93005405&format=json"))
                .enrich().simple("netty-http:https://openlibrary.org/api/books")
                .log("meaning ${body}");
    }
}
