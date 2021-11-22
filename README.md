#For running

1. create docker image `mvn clean spring-boot:build-image`
2. if any code changes done delete old image first
3. following will be created because of `auto-declare` 
   create `myExchange` `myQueue` and `myRoutingKey` from 
   rabbitmq admin http://localhost:15672/ using `guest:guest`
4. run `docker-compose up`

## Useful links:
https://spring.io/blog/2010/06/14/understanding-amqp-the-protocol-used-by-rabbitmq

https://camel.apache.org/components/next/spring-rabbitmq-component.html 