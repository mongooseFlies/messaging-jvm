package org.messaging.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import org.messaging.api.EventMessaging
import org.messaging.config.Profiles
import org.messaging.dto.Event
import org.messaging.exception.ApiException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.kafka.KafkaException
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
@Profile(Profiles.KAFKA)
class EventProducer(
    @Autowired val kafkaTemplate: KafkaTemplate<String, String>,
    @Value("\${kafka.queue.notification.create}")
    private val kafkaCreateEventTopic: String,
    @Value("\${kafka.queue.notification.update}")
    private val kafkaUpdateEventTopic: String,
    @Value("\${kafka.queue.notification.delete}")
    private val kafkaDeleteEventTopic: String,
    private val mapper: ObjectMapper
) : EventMessaging {

    override fun createEvent(event: Event) {
        try {
            kafkaTemplate.send(kafkaCreateEventTopic, mapper.writeValueAsString(event))
        } catch (ex: KafkaException) {
            throw ApiException("Kafka broker exception while sending to the queue", HttpStatus.SERVICE_UNAVAILABLE.value())
        }
    }

    override fun updateEvent(event: Event) {
        try {
            kafkaTemplate.send(kafkaUpdateEventTopic, mapper.writeValueAsString(event))
        } catch (ex: KafkaException) {
            throw ApiException("Kafka broker exception while updating into into queue", HttpStatus.SERVICE_UNAVAILABLE.value())
        }
    }

    override fun deleteEvent(id: Long) {
        try {
            val message = "Event with id[$id] has been deleted"
            kafkaTemplate.send(kafkaDeleteEventTopic, message)
        } catch (ex: KafkaException) {
            throw ApiException("Kafka broker exception while deleting from the queue", HttpStatus.SERVICE_UNAVAILABLE.value())
        }
    }
}