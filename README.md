## Messaging system demo { Kafka, Active MQ, Rabbit MQ }

***
### Spring boot Rest application demonstrating working with different messaging systems with following modules :

- event-service-api
- event-service-dto
- event-service-impl
- event-service-rest

### Application produce and consumer messages from :

- Kafka
- RabbitMQ
- ActiveMQ

## TODO

- [x] depending on Spring active profile it interacts with the corresponding messaging systems 
    - [x] Kafka
    - [x] RabbitMQ
    - [x] ActiveMQ
- [x] Controller API calls send events to MS accordingly
- [x] Consumer listens for incoming messages and prints them
