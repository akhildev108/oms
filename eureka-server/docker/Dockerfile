FROM openjdk:11.0.10-jre-slim-buster

COPY /target/eureka-server-0.0.1-SNAPSHOT.jar deployments/eureka-server-0.0.1-SNAPSHOT.jar

# ARG config_server_url_arg=http://localhost:8888
# ENV CONFIG_SERVER_URL=$config_server_url_arg

EXPOSE 8761

ENTRYPOINT ["java","-jar","deployments/eureka-server-0.0.1-SNAPSHOT.jar"]