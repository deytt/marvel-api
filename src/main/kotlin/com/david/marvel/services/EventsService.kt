package com.david.marvel.services

import com.david.marvel.model.Comics
import com.david.marvel.model.Events
import java.util.*

interface EventsService {
    fun create(events: Events): Events
    fun getAll(): List<Events>
    fun findById(id: Long): Optional<Events>
    fun update(id: Long, events: Events): Optional<Events>
    fun delete(id: Long)
}