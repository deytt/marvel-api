package com.david.marvel.services

import com.david.marvel.model.Series
import com.david.marvel.repository.SeriesRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class SeriesServiceImpl(private val repository: SeriesRepository) : SeriesService {
    override fun create(series: Series): Series {
        return repository.save(series)
    }

    override fun getAll(): List<Series> {
        return repository.findAll()
    }

    override fun findById(id: Long): Optional<Series> {
        return repository.findById(id)
    }

    override fun delete(id: Long) {
        repository.findById(id).map {
            repository.delete(it)
        }.orElseThrow { throw RuntimeException("[ID: $id] not found") }
    }

    override fun update(id: Long, series: Series): Optional<Series> {
        val optional = findById(id)
        if (!optional.isPresent) return Optional.empty<Series>()
        return optional.map {
            val comicsToUpdate = it.copy(
                    characters = series.characters,
                    name = series.name,
                    title = series.title,
                    description = series.description,
                    modified = series.modified
            )
            repository.save(comicsToUpdate)
        }
    }
}