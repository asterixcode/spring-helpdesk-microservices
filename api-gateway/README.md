# API Gateway

API Gateway using Spring Cloud Gateway.

## Running the project locally

- Create a .env file in the root of the project with the following content:

```properties
SPRING_PROFILES_ACTIVE=dev
SERVER_PORT=8765
CONFIG_SERVER_URI=http://config-server:8888
EUREKA_URI=http://service-discovery:8761/eureka
```