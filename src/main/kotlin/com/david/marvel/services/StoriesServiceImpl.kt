package com.david.marvel.services

import com.david.marvel.model.Stories
import com.david.marvel.repository.StoriesRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class StoriesServiceImpl(private val repository: StoriesRepository) : StoriesService {
    override fun create(stories: Stories): Stories {
        return repository.save(stories)
    }

    override fun getAll(): List<Stories> {
        return repository.findAll()
    }

    override fun findById(id: Long): Optional<Stories> {
        return repository.findById(id)
    }

    override fun delete(id: Long) {
        repository.findById(id).map {
            repository.delete(it)
        }.orElseThrow { throw RuntimeException("[ID: $id] not found") }
    }

    override fun update(id: Long, stories: Stories): Optional<Stories> {
        val optional = findById(id)
        if (!optional.isPresent) return Optional.empty<Stories>()
        return optional.map {
            val comicsToUpdate = it.copy(
                    characters = stories.characters,
                    name = stories.name,
                    title = stories.title,
                    description = stories.description
            )
            repository.save(comicsToUpdate)
        }
    }
}