package com.camel.bootcamel.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        //warn: this don't work in container cause this folder is not found
        from("file:source-folder?noop=true")
                .log("aaa")
                .filter(header(Exchange.FILE_NAME).startsWith("forMoving"))
                    .choice()
                        .when(body().contains("rock"))
                            .log("moving a rock")
                        .when(body().contains("paper"))
                            .log("moving a paper")
                        .otherwise()
                            .log("moving a regular one")
                            .to("direct:regular-processor")
                    .end()
                .to("file:destination-folder");

        from("direct:regular-processor")
                .log("dat shit is crazy");
    }
}
