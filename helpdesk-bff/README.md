# BFF

This project is the entry point for the Helpdesk application.

## Running the project locally

- Create a .env file in the root of the project with the following content:

```properties
SPRING_PROFILES_ACTIVE=dev

EUREKA_URI=http://localhost:8761/eureka
CONFIG_SERVER_URI=http://localhost:8888
REDIS_HOST=redis
REDIS_PORT=6379

JWT_SECRET=<your_secret>
```

The private key (JWT_SECRET) must be the same as the one used in the auth-service-api to sign the JWT token.