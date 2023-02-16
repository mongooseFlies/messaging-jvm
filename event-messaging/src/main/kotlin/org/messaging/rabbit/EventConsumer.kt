package org.messaging.rabbit

import org.messaging.config.Profiles
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile(Profiles.RABBIT_MQ)
class EventConsumer {

    @RabbitListener(queues = ["\${rabbit.queue.request.create}"])
    fun createEvent(event: String) =
        println("CreateEvent received = $event")

    @RabbitListener(queues = ["\${rabbit.queue.request.update}"])
    fun updateEvent(event: String) =
        println("UpdateEvent received = $event")

    @RabbitListener(queues = ["\${rabbit.queue.request.delete}"])
    fun deleteEvent(eventId: Long) =
        println("DeleteEvent received = Event(id[$eventId] deleted")

}