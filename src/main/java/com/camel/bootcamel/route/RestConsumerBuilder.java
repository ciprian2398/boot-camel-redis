package com.camel.bootcamel.route;

import com.camel.bootcamel.model.User;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static org.apache.camel.model.rest.RestParamType.path;

@Component
@Profile("!exclusive-config")
public class RestConsumerBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("netty-http")
                    .host("localhost").port(8080)
                    .bindingMode(RestBindingMode.json)
                    .dataFormatProperty("prettyPrint","true")
        .apiContextPath("/api-doc")
                .apiProperty("api.title", "User API").apiProperty("api.version", "v1")
                .apiProperty("cors", "true");

        rest("/user").description("User rest service")
                .consumes("application/json").produces("application/json")
                .get("/{id}").description("Find user by id").outType(User.class)
                    .param().name("id").type(path).description("The id of the user to get").dataType("int").endParam()
                    .to("bean:userService?method=getUser(${header.id})");

    }
}
