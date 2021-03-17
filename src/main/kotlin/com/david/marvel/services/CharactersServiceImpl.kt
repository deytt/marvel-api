package com.david.marvel.services

import com.david.marvel.model.*
import com.david.marvel.repository.CharactersRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class CharactersServiceImpl(private val repository: CharactersRepository) : CharactersService {

    override fun ping(): ResponseEntity<String> {
        return ResponseEntity.ok(ObjectMapper().writeValueAsString(mapOf("success" to "Server is up")))
    }

    override fun create(character: Character): Character {
        return repository.save(character)
    }

    override fun getAll(): List<Character> {
        return repository.findAll()
    }

    override fun findById(id: Long): Optional<Character> {
        return repository.findById(id)
    }

    override fun delete(id: Long) {
        repository.findById(id).map {
            repository.delete(it)
        }.orElseThrow { throw RuntimeException("[ID: $id] not found") }
    }

    override fun update(id: Long, character: Character): Optional<Character> {
        val optional = findById(id)
        if (!optional.isPresent) return Optional.empty<Character>()
        return optional.map {
            val characterToUpdate = it.copy(
                    name = character.name,
                    description = character.description
            )
            repository.save(characterToUpdate)
        }
    }

    override fun getRelatedComics(id: Long): List<Comics> {
        return repository.findComicsByCharacterId(id).map { it[0] }
    }

    override fun getRelatedEvents(id: Long): List<Events> {
        return repository.findEventsByCharacterId(id).map { it[0] }
    }

    override fun getRelatedSeries(id: Long): List<Series> {
        return repository.findSeriesByCharacterId(id).map { it[0] }
    }

    override fun getRelatedStories(id: Long): List<Stories> {
        return repository.findStoriesByCharacterId(id).map { it[0] }
    }
}