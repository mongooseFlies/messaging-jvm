package org.messaging.impl

import org.messaging.api.EventMessaging
import org.messaging.api.EventService
import org.messaging.dto.Event
import org.messaging.impl.exceptions.EventNotFoundException
import org.messaging.impl.repository.EventRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EventServiceImpl(
    @Autowired val repository: EventRepository,
    @Autowired(required = false) val eventMessaging: EventMessaging?
) : EventService {

    override fun createEvent(event: Event): Event =
        repository.save(event).also { eventMessaging?.createEvent(event) }

    override fun updateEvent(id: Long, event: Event): Event =
        with(repository) {
            getEvent(id).also { save(event.copy(eventId = id)) }
        }.also { eventMessaging?.updateEvent(event) }

    override fun getEvent(id: Long): Event =
        repository.findById(id)
            .orElseThrow { EventNotFoundException(id) }

    override fun deleteEvent(id: Long): Event =
        getEvent(id).also {
            repository.deleteById(id)
            eventMessaging?.deleteEvent(id)
        }


    override fun getAllEvent(): List<Event> =
        repository.findAll()
}