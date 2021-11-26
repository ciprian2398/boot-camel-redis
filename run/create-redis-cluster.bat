@echo off

@REM docker-compose -f docker-compose-redis.yml down

@REM will display the network
@REM docker network inspect app-tier


@REM docker-compose -f docker-compose-redis.yml up -d

@REM getting the container inner ip and writting in file
docker inspect -f {{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}} redis0 > redis0IPAddress.tmp
@REM reading from file
set /p redis0IPAddress=<redis0IPAddress.tmp

docker inspect -f {{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}} redis1 > redis1IPAddress.tmp
set /p redis1IPAddress=<redis1IPAddress.tmp

docker inspect -f {{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}} redis2 > redis2IPAddress.tmp
set /p redis2IPAddress=<redis2IPAddress.tmp

docker inspect -f {{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}} redis3 > redis3IPAddress.tmp
set /p redis3IPAddress=<redis3IPAddress.tmp

docker inspect -f {{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}} redis4 > redis4IPAddress.tmp
set /p redis4IPAddress=<redis4IPAddress.tmp

docker inspect -f {{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}} redis5 > redis5IPAddress.tmp
set /p redis5IPAddress=<redis5IPAddress.tmp

del /S *.tmp



set clusterString=%redis0IPAddress%:6379 %redis1IPAddress%:6379 %redis2IPAddress%:6379 %redis3IPAddress%:6379 %redis4IPAddress%:6379 %redis5IPAddress%:6379
echo %clusterString%

set command= redis-cli -h %redis0IPAddress% -p 6379 --cluster create %clusterString% --cluster-replicas 1 --cluster-yes
@REM echo %command%

docker exec -it redis0 %command%

docker exec -it redis0 redis-cli -c -p 6379 cluster nodes

