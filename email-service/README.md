# Email Service

Email service that sends emails consuming messages from RabbitMQ and sending over a SMTP server.

## Running the project locally

- Create a .env file in the root of the project with the following content:

```properties
SPRING_PROFILES_ACTIVE=dev

RABBITMQ_PORT=5672 # default port for RabbitMQ
RABBITMQ_ADDRESSES=rabbitmq # default address for RabbitMQ
RABBITMQ_USERNAME=guest # default username for RabbitMQ
RABBITMQ_PASSWORD=guest # default password for RabbitMQ

MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=<your-email>
MAIL_PASSWORD=<your-password>
MAIL_PROTOCOL=smtp

CONFIG_SERVER_URI=http://config-server:8888
EUREKA_URI=http://service-discovery:8761/eureka

SERVER_PORT=8080
```