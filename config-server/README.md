# Config Server

Configuration server for the other services in the system.

## Running the project locally

- Create a .env file in the root of the project with the following content:

```properties
CONFIG_SERVER_URI=<config-server-uri> # e.g. a URL to a git repository
CONFIG_SERVER_USERNAME=<config-server-username> # e.g. a github username
CONFIG_SERVER_PASSWORD=<config-server-password> # e.g. a github classic token
SERVER_PORT=8888
EUREKA_URI=http://service-discovery:8761/eureka
```