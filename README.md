#For running

1. create docker image `mvn clean spring-boot:build-image`
2. if any code changes done delete old image first
3. manually create `myExchange` `myQueue` and `myRoutingKey` from rabbitmq admin http://localhost:15672/ using `guest:guest`
4. run `docker-compose up`