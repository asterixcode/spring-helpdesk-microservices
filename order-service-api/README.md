# Order Service API

Microservice responsible for managing orders over REST API.

## Running the project locally

- Create a .env file in the root of the project with the following content:

```properties
SPRING_PROFILES_ACTIVE=dev

DATASOURCE_URL=<your_db_url> # e.g. jdbc:postgresql://localhost:5432/helpdesk
DATASOURCE_USERNAME=<your_db_username> # e.g. postgres
DATASOURCE_PASSWORD=<your_db_password> # e.g. postgres
DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver
JPA_HIBERNATE_DDL_AUTO=update

RABBITMQ_HOST=localhost
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=guest
RABBITMQ_PASSWORD=guest
RABBITMQ_CONNECTION_TIMEOUT=10000

CONFIG_SERVER_URI=http://config-server:8888
EUREKA_URI=http://service-discovery:8761/eureka
```