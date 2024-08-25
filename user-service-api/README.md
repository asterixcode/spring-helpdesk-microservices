# User Service API

Microservice that provides user management functionalities over REST API.

## Running the project locally

- Create a .env file in the root of the project with the following content:

```properties
SPRING_PROFILES_ACTIVE=dev

MONGODB_USERNAME=<your-mongodb-username> # e.g. help-desk-user
MONGODB_PASSWORD=<your-mongodb-password> # e.g. help-desk-user
MONGODB_DATABASE=<your-mongodb-database> # e.g. helpdesk-db
MONGODB_CLUSTER=<your-mongodb-cluster> # e.g. cluster0.abcde.mongodb.net
MONGODB_APP_NAME=<your-mongodb-app-name> # e.g. ClusterHelpDesk

CONFIG_SERVER_URI=http://config-server:8888
EUREKA_URI=http://service-discovery:8761/eureka
```
