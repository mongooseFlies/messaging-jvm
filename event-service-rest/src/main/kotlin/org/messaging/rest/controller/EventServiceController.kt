package org.messaging.rest.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.messaging.api.EventService
import org.messaging.dto.Event
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/event")
@Tag(name = "event", description = "the Event API with documentation annotations")
class EventServiceController(
    @Autowired val eventService: EventService
) {
    @Operation(summary = "Get Event By ID")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Event Found", content = [
                    (Content(
                        mediaType = "application/json", array = (
                                ArraySchema(schema = Schema(implementation = Event::class)))
                    ))]
            ),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
            ApiResponse(responseCode = "404", description = "Event Not Found", content = [Content()])]
    )
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/{id}"],
        produces = ["application/json"]
    )
    fun getEvent(@PathVariable("id") id: Long) =
        ResponseEntity(eventService.getEvent(id), HttpStatus.OK)

    @Operation(summary = "Create Event")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201", description = "Event Created",
                content = [
                    (Content(
                        mediaType = "application/json", array = (
                                ArraySchema(schema = Schema(implementation = Event::class)))
                    ))],
            ),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
        ]
    )
    @RequestMapping(
        method = [RequestMethod.POST],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun createEvent(@RequestBody event: Event) =
        ResponseEntity(eventService.createEvent(event), HttpStatus.CREATED)

    @Operation(summary = "Update Event")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Event Updated", content = [
                    (Content(
                        mediaType = "application/json", array = (
                                ArraySchema(schema = Schema(implementation = Event::class)))
                    ))]
            ),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
            ApiResponse(responseCode = "404", description = "Event Not Found", content = [Content()])]
    )
    @RequestMapping(
        method = [RequestMethod.PUT],
        value = ["/{id}"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun updateEvent(@PathVariable("id") id: Long, @RequestBody event: Event) =
        ResponseEntity(eventService.updateEvent(id, event), HttpStatus.OK)

    @Operation(summary = "Delete Event")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Event Deleted", content = [
                    (Content(
                        mediaType = "application/json", array = (
                                ArraySchema(schema = Schema(implementation = Event::class)))
                    ))]
            ),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
            ApiResponse(responseCode = "404", description = "Event Not Found", content = [Content()])]
    )
    @RequestMapping(
        method = [RequestMethod.DELETE],
        value = ["/{id}"]
    )
    fun deleteEvent(@PathVariable id: Long) =
        ResponseEntity(eventService.deleteEvent(id), HttpStatus.NO_CONTENT)

    @Operation(summary = "Get All Event")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Event Updated", content = [
                    (Content(
                        mediaType = "application/json", array = (
                                ArraySchema(schema = Schema(implementation = Event::class)))
                    ))]
            ),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
            ApiResponse(responseCode = "404", description = "Event Not Found", content = [Content()])]
    )
    @RequestMapping(
        method = [RequestMethod.GET],
        produces = ["application/json"]
    )
    fun getAllEvents() =
        ResponseEntity(eventService.getAllEvent(), HttpStatus.OK)
}