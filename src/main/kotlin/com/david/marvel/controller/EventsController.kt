package com.david.marvel.controller

import com.david.marvel.model.Events
import com.david.marvel.services.EventsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/public/events")
class EventsController(private val service: EventsService) {
    @GetMapping
    fun getAll(): List<Events> = service.getAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<Events> =
            service.findById(id).map {
                ResponseEntity.ok(it)
            }.orElseThrow { throw RuntimeException("[ID: $id] not found") }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody events: Events): Events = service.create(events)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody events: Events): ResponseEntity<Events> =
            service.update(id, events).map {
                ResponseEntity.ok(it)
            }.orElseThrow { throw RuntimeException("[ID: $id] not found") }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity(HttpStatus.OK)
    }
}