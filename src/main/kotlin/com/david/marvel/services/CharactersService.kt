package com.david.marvel.services

import com.david.marvel.model.*
import org.springframework.http.ResponseEntity
import java.util.Optional

interface CharactersService {
    fun create(character: Character): Character
    fun getAll(): List<Character>
    fun findById(id: Long): Optional<Character>
    fun update(id: Long, character: Character): Optional<Character>
    fun delete(id: Long)
    fun getRelatedComics(id: Long): List<Comics>
    fun getRelatedEvents(id: Long): List<Events>
    fun getRelatedSeries(id: Long): List<Series>
    fun getRelatedStories(id: Long): List<Stories>
    fun ping(): ResponseEntity<String>
}