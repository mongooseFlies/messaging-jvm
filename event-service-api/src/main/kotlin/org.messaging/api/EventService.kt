package org.messaging.api

import org.messaging.dto.Event

interface EventService {
    fun createEvent(event: Event): Event
    fun updateEvent(id: Long, event: Event): Event
    fun getEvent(id: Long): Event
    fun deleteEvent(id: Long): Event
    fun getAllEvent(): List<Event>
}