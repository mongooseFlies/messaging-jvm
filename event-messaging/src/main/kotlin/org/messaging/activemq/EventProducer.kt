package org.messaging.activemq

import com.fasterxml.jackson.databind.ObjectMapper
import org.messaging.api.EventMessaging
import org.messaging.config.Profiles
import org.messaging.dto.Event
import org.messaging.exception.ApiException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.jms.JmsException
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Component

@Component
@Profile(Profiles.ACTIVE_MQ)
class EventProducer(
    @Autowired val jmsTemplate: JmsTemplate,
    @Value("\${activemq.queue.notification.create}")
    private val activemqCreateEventTopic: String,
    @Value("\${activemq.queue.notification.update}")
    private val activemqUpdateEventTopic: String,
    @Value("\${activemq.queue.notification.delete}")
    private val activemqDeleteEventTopic: String,
    private val mapper: ObjectMapper
) : EventMessaging {
    override fun createEvent(event: Event) =
        mapper.writeValueAsString(event).let {
            try {
                jmsTemplate.convertAndSend(activemqCreateEventTopic, it)
            } catch (ex: JmsException) {
                throw ApiException(
                    "Activemq broker exception while creating event into queue",
                    HttpStatus.SERVICE_UNAVAILABLE.value()
                )
            }
        }

    override fun updateEvent(event: Event) =
        mapper.writeValueAsString(event).let {
            try {
                jmsTemplate.convertAndSend(activemqUpdateEventTopic, it)
            } catch (ex: JmsException) {
                throw ApiException(
                    "Activemq broker exception while updating event in the Activemq queue",
                    HttpStatus.SERVICE_UNAVAILABLE.value()
                )
            }
        }

    override fun deleteEvent(id: Long) =
        try {
            jmsTemplate.convertAndSend(activemqDeleteEventTopic, id)
        } catch (ex: JmsException) {
            throw ApiException(
                "Activemq broker exception while deleting event(id=$id) into the Activemq queue",
                HttpStatus.SERVICE_UNAVAILABLE.value()
            )
        }
}