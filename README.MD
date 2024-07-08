Order Management System:
--------------------------------------
This application currently consists of 4 microservices (config-server, eureka-server, customerservice, orderservice).
Communication between customerservice and orderservice occurs over HTTP/REST. 
This is a microservices based Spring Boot application. 

config-server is a Spring Cloud Config Server. 
netflix eureka-server is used for service registry and discovery.
Customer service is used to perform CRUD operations on Customer resource. 
Order service is used to perform CRUD opearions on Order resource.


Pre-requisites:
------------------
1. Docker Engine and Docker CLI (e.g Docker for Desktop for Windows and Mac)
2. Powershell for running docker commands
3. Postman or Insomnia or curl or similar utility to access Customer and Order REST APIs


Steps to deploy the application/microservices (config-server, eureka-server, customerservice, orderservice)
---------------------------------------------------------------------------------------------------------------------------------------
1. Install Docker Desktop
2. Checkout the code from the github

3. Change directory to `\eureka-server` then compile and package (mvn clean package) eureka-server source code.
4. Copy `\target\eureka-server-0.0.1-SNAPSHOT.jar` file into `\eureka-server\docker\target` folder.

5. Then change directory to `eureka-server\docker` and execute following command to create eureka-server image (which uses Dockerfile)
> docker image build -t eureka-server:0.1 .

6. Now CD to `docker` and execute following command: 

> docker-compose -f docker-compose-for-development.yml up -d
(To stop and remove docker containers, execute: > docker-compose -f docker-compose-for-development.yml down)

7. Now use `docker ps` to know the container IDs and container status (to make sure all 4 containers started successfully)
 
8. Now CD to `\customer` and `\order` to complie and package (mvn clean package) customer and order source codes respectively

9. Start customer-service by executing following command:
> docker exec {customerservice_container_id} java -jar /deployments/customer-services-0.0.1-SNAPSHOT.jar

10. Start order-service by executing following command: 
> docker exec {orderservice_container_id} java -jar /deployments/order-services-0.0.1-SNAPSHOT.jar


Steps to access the microservices REST APIs
-----------------------------------------------------------
To tccess eureka-server: http://localhost:8761/

To view customerservice and orderservices configuration on Spring Clound Config Server:
http://localhost:8888/customerservices/default and http://localhost:8888/orderservices/default

To access Customer REST APIs:

POST: http://localhost:8800/customers
{
    "firstName": "John",
    "lastName": "Doe",
    "email": "jd@test.test"
}
GET http://localhost:8800/customers/1

GET http://localhost:8800/customers

GET http://localhost:8800/customers/1/orders

GET http://localhost:8800/customers/1/exists

PUT http://localhost:8800/customers/1
{
    "id": 1,
    "firstName": "John-upd1",
    "lastName": "Doe",
    "email": "jd@test.test"
}

To access Order REST APIs:

POST http://localhost:8801/orders
{
    "customerId" : 1,
    "description" : "2 512 GB SSD",
    "email" : "test@ac.test",
    "phoneNumber" : "000-000-0001"
}

GET http://localhost:8801/orders/1

GET http://localhost:8801/orders

PUT http://localhost:8801/orders/1
{
    "id": 1,
    "customerId": 1,
    "description": "2 512 GB SSD - updated now 3 SSDs",
    "email": "test@ac.test",
    "phoneNumber": "000-000-0001"
}

Unit tests
--------------
Currently, I have written following two classes for unit testing REST endpoints

1. CustomerControllerTest.java
2. OrderControllerTest.java
