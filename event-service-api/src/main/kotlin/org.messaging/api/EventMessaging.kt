package org.messaging.api

import org.messaging.dto.Event

interface EventMessaging {
    fun createEvent(event: Event)
    fun updateEvent(event: Event)
    fun deleteEvent(id: Long)
}