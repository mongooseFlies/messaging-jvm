package org.messaging.activemq

import org.messaging.config.Profiles
import org.springframework.context.annotation.Profile
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component

@Component
@Profile(Profiles.ACTIVE_MQ)
@EnableJms
class EventConsumer {
    @JmsListener(destination = "\${activemq.queue.request.create}")
    fun createEvent(event: String) =
        println("CreateEvent received = $event")

    @JmsListener(destination = "\${activemq.queue.request.update}")
    fun updateEvent(event: String) =
        println("UpdateEvent received = $event")

    @JmsListener(destination = "\${activemq.queue.request.delete}")
    fun deleteEvent(eventId: Long) =
        println("DeleteEvent received = Event(id[$eventId]) deleted")
}