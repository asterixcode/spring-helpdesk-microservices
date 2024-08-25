# Auth Service API

Microservice responsible for managing user authentication and refresh tokens over REST API.

It uses JWT for authentication and refresh tokens to keep the user logged in.

## Running the project locally

- Create a .env file in the root of the project with the following content:

```properties
SPRING_PROFILES_ACTIVE=dev

JWT_SECRET=<generate_your_jwt_secret>
JWT_EXPIRATION=3600000 # 1 hour
JWT_EXPIRATION_SECONDS_REFRESH_TOKEN=100 # 100 seconds

MONGODB_USERNAME=<your_mongodb_user> # e.g. help-desk-user
MONGODB_PASSWORD=<your_mongodb_password> # e.g. help-desk-password
MONGODB_DATABASE=<your_mongodb_database> # e.g. helpdesk-db
MONGODB_CLUSTER=<your_cluster_uri> # e.g. cluster0.abcde.mongodb.net
MONGODB_APP_NAME=<your-mongodb-app-name> # e.g. ClusterHelpDesk

CONFIG_SERVER_URI=http://config-server:8888
EUREKA_URI=http://service-discovery:8761/eureka
```

## Generate a JWT secret

Suggestion:

- go to https://generate-random.org/encryption-key-generator
- select 64 bytes (as the service is using HS512 algorithm)
- copy the generated key and paste it in the .env file JWT_SECRET env variable