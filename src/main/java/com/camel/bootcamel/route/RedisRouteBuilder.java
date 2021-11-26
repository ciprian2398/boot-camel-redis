package com.camel.bootcamel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty("app.feature.redis-route-enabled")
public class RedisRouteBuilder extends RouteBuilder {

    @Value("${app.redis.host}")
    private String redisHost;

    @Value("${app.redis.port}")
    private Integer redisPort;

    @Override
    public void configure() throws Exception {

        from("timer:myTimer?repeatCount=1")
                .id("RedisRouteBuilder-timer-writer")
                .setHeader("CamelRedis.Command", () -> "SET")
                .setHeader("CamelRedis.Key", () -> "keyOne")
                .setHeader("CamelRedis.Value", simple("1", String.class))
                .process(exchange -> exchange.getIn().setBody("1"))//prevents npe
                .to("spring-redis:" + redisHost + redisPort)
                .log("GOOOOO")
                .to("direct:lol");

        from("direct:lol")
                .log("sent : ${headers} and ${body}")
                .setHeader("CamelRedis.Command", () -> "APPEND" )
                .setHeader("CamelRedis.Value", simple("${body}", String.class))
                .choice()
                .when(exchange -> exchange.getIn().getBody(String.class).length() < 3)
                    .to("spring-redis:" + redisHost + redisPort)
                    .to("direct:lol")
                .otherwise()
                    .log("finish")
                .endChoice();
    }
}
