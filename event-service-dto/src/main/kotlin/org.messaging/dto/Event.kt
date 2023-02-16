package org.messaging.dto

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "EVENT")
data class Event(
    @Id @GeneratedValue
    val eventId: Long?,
    val title: String,
    val place: String,
    val speaker: String,
    val eventType: EventType,
    val dateTime: LocalDateTime
) {
    constructor() : this(
        eventId = 0,
        title = "",
        place = "",
        speaker = "",
        eventType = EventType.WORKSHOP,
        dateTime = LocalDateTime.now()
    )
}

