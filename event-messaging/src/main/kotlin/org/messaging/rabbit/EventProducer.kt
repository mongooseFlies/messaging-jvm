package org.messaging.rabbit

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.errors.ApiException
import org.messaging.api.EventMessaging
import org.messaging.config.Profiles
import org.messaging.dto.Event
import org.springframework.amqp.AmqpException
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile(Profiles.RABBIT_MQ)
class EventProducer(
    val rabbitTemplate: RabbitTemplate,
    @Value("\${rabbit.queue.notification.create}")
    val createQueueName: String,
    @Value("\${rabbit.queue.notification.update}")
    val updateQueueName: String,
    @Value("\${rabbit.queue.notification.delete}")
    val deleteQueueName: String,
    @Autowired val mapper: ObjectMapper
) : EventMessaging {
    override fun createEvent(event: Event) =
        mapper.writeValueAsString(event).let {
            try {
                rabbitTemplate.convertAndSend(createQueueName, it)
            } catch (ex: AmqpException) {
                throw ApiException("Rabbitmq broker exception while sending into the queue")
            }
        }

    override fun updateEvent(event: Event) =
        mapper.writeValueAsString(event).let {
            try {
                rabbitTemplate.convertAndSend(updateQueueName, it)
            } catch (ex: AmqpException) {
                throw ApiException("Rabbitmq broker exception while sending into the queue")
            }
}

    override fun deleteEvent(id: Long) {
        try {
            rabbitTemplate.convertAndSend(deleteQueueName, id)
        } catch (ex: AmqpException) {
            throw ApiException("Rabbitmq broker exception while sending into the queue")
        }
    }
}