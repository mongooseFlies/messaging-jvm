package org.messaging.kafka

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.messaging.config.Profiles
import org.springframework.context.annotation.Profile
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
@Profile(Profiles.KAFKA)
class EventConsumer {

    @KafkaListener(topics = ["\${kafka.queue.request.create}"])
    fun createEvent(customerRecord: ConsumerRecord<Any, Any>) =
        println("CreateEvent message received = $customerRecord")

    @KafkaListener(topics = ["\${kafka.queue.request.update}"])
    fun updateEvent(customerRecord: ConsumerRecord<Any, Any>) =
        println("UpdateEvent message received = $customerRecord")

    @KafkaListener(topics = ["\${kafka.queue.request.delete}"])
    fun deleteEvent(customerRecord: ConsumerRecord<Any, Any>) =
        println("DeleteEvent => $customerRecord")
}