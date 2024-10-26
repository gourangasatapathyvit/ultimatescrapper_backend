
**run redis**

`docker run --name redisServe --network scrape-net -p 6379:6379 -d redis:latest`

**RUN scrape service**

`docker run --name scrape-service --network scrape-net -p 8090:8090 -e SPRING_PROFILE=docker -e REDIS_HOST=redisServe -e SPRING_LOGGING.LEVEL=debug -d gouranga1234/scrapeservice:1.2.8`

**RUN API GATEWAY**

`docker run --name gateway-service --network scrape-net -p 8084:8084 -e SPRING_PROFILE=docker -e REDIS_HOST=redisServe -e SPRING_LOGGING.LEVEL=debug -e LB_SCRAPESERVICE_URL=http://scrape-service:8090 -d gouranga1234/apigateway:1.0.9`