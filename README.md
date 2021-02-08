Weather Collector Service
---

This is a simple application that requests its data from [OpenWeather](https://openweathermap.org/) 
and stores the result in a database. 

## Requirements

The following components are needed at least to build and run the service.
+ Java 11
+ Maven 3.3.9
+ Docker 19
+ [OpenWeather API Key](https://openweathermap.org/appid)

## Major Improvements

+ The code structure has been redefined.
+ New classes are created to adapt the single responsibility.
+ RestTemplate is replaced with OkHttp and also OpenFeign is applied as the client interface.
+ Error handling is added for both OpenWeather client and Rest service.
+ Outdated postgresql driver is replaced with newer one.
+ Liquibase is added to manage database changes in production over time.
+ Unit and Integration tests are added.
+ OpenApi is applied to generate docs for anyone who wants to use this API in production.
 

## Build

Go to the root of the project and run the following command:

```
mvn clean install jib:dockerBuild
```

Notes:

+ If you do not have docker daemon on your machine, you could exclude jib execution from it.
```
mvn clean install 
```

## Running service

### With docker compose

+ You need to install docker compose in your local machine
+ Be sure that docker image is built using the following command:

```
docker images weather-collector-service
```

+ Go to the `src/main/resources/docker` and update `OPENWEATHER_SERVICE_ID` variable inside docker compose file 
with you OpenWeather key. Now, run the following commands in the order:

```
docker-compose up -d database
```
```
docker-compose up weather-collector-service
```

### Without docker

+ You need to have a postgresql database installed on your machine

+ Set following OS environment variables in your machine

```
OPENWEATHER_SERVICE_ID={OpenWeather api key}
SPRING_DATASOURCE_URL={Datasource jdbc url}
SPRING_DATASOURCE_USERNAME={Datasource username}
SPRING_DATASOURCE_PASSWORD={Datasource password}
```
+ Run the following command in the target folder:
```
java -jar weather-collector-service-0.1.0-SNAPSHOT.jar
```

### Access Doc Api

If service is up successfully, you could access the service doc api from here:
```
GET http://localhost:8080/swagger-ui.html
```

## Proposed Enhancements

It is also possible to make these changes in order to improve the quality of service in the further releases.

+ Apply reactive web api and replace tomcat with Netty.
+ Add extra cache layer to the service api in order to decrease the load on network and db.