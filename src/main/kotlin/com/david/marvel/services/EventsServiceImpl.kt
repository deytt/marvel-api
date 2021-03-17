package com.david.marvel.services

import com.david.marvel.model.Events
import com.david.marvel.repository.EventsRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class EventsServiceImpl(private val repository: EventsRepository) : EventsService {
    override fun create(events: Events): Events {
        return repository.save(events)
    }

    override fun getAll(): List<Events> {
        return repository.findAll()
    }

    override fun findById(id: Long): Optional<Events> {
        return repository.findById(id)
    }

    override fun delete(id: Long) {
        repository.findById(id).map {
            repository.delete(it)
        }.orElseThrow { throw RuntimeException("[ID: $id] not found") }
    }

    override fun update(id: Long, events: Events): Optional<Events> {
        val optional = findById(id)
        if (!optional.isPresent) return Optional.empty<Events>()
        return optional.map {
            val comicsToUpdate = it.copy(
                    characters = events.characters,
                    name = events.name,
                    title = events.title,
                    description = events.description
            )
            repository.save(comicsToUpdate)
        }
    }
}