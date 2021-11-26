@echo off

echo Removing old image
docker rmi -f boot-camel:0.0.1-SNAPSHOT

echo Building new image
call mvnw clean spring-boot:build-image -DskipTests=true

docker-compose up -d redis0

call run/create-redis-cluster.bat

echo Restart app
docker-compose up -d app

