package com.camel.bootcamel.route;

import com.camel.bootcamel.model.User;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.apache.camel.model.rest.RestParamType.path;

@Component
@ConditionalOnProperty("app.feature.rest-route-enabled")
public class RestConsumerBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .component("netty-http")
                .host("0.0.0.0").port(8080)
                .bindingMode(RestBindingMode.json)
                    .dataFormatProperty("prettyPrint","true")
        .apiContextPath("/api-doc")
                .apiProperty("api.title", "User API").apiProperty("api.version", "v1")
                .apiProperty("cors", "true");

        rest("/user").description("User rest service")
                .consumes("application/json").produces("application/json")
                .get("/create/{name}").outType(User.class)
                    .param().name("id").type(path).description("The name of user to create").dataType("string").endParam()
                    .to("bean:userService?method=createUser(${header.name})")
                .get("/{id}").description("Find user by id").outType(User.class)
                    .param().name("id").type(path).description("The id of the user to get").dataType("string").endParam()
                    .to("bean:userService?method=getUser(${header.id})")
                .get("/all").description("Find all users").outType(List.class)
                .to("bean:userService?method=getAll()");

    }
}
