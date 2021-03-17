package com.david.marvel.services

import com.david.marvel.model.Comics
import com.david.marvel.repository.ComicsRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ComicsServiceImpl(private val repository: ComicsRepository) : ComicsService {

    override fun create(comics: Comics): Comics {
        return repository.save(comics)
    }

    override fun getAll(): List<Comics> {
        return repository.findAll()
    }

    override fun findById(id: Long): Optional<Comics> {
        return repository.findById(id)
    }

    override fun delete(id: Long) {
        repository.findById(id).map {
            repository.delete(it)
        }.orElseThrow { throw RuntimeException("[ID: $id] not found") }
    }

    override fun update(id: Long, comics: Comics): Optional<Comics> {
        val optional = findById(id)
        if (!optional.isPresent) return Optional.empty<Comics>()
        return optional.map {
            val comicsToUpdate = it.copy(
                    characters = comics.characters,
                    name = comics.name,
                    title = comics.title,
                    description = comics.description
                    )
            repository.save(comicsToUpdate)
        }
    }

}