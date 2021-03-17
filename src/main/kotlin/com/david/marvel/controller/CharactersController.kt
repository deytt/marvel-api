package com.david.marvel.controller

import com.david.marvel.model.Character
import com.david.marvel.services.CharactersService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/public/characters")
class CharactersController(private val service: CharactersService) {

    @GetMapping
    fun getAll(): List<Character> = service.getAll()

    @GetMapping("/ping")
    fun ping(): ResponseEntity<String> = service.ping()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<Character> =
            service.findById(id).map {
                ResponseEntity.ok(it)
            }.orElseThrow { throw RuntimeException("[ID: $id] not found") }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody character: Character): Character = service.create(character)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody character: Character): ResponseEntity<Character> =
            service.update(id, character).map {
                ResponseEntity.ok(it)
            }.orElseThrow { throw RuntimeException("[ID: $id] not found") }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity(HttpStatus.OK)
    }

    //TODO implementar mensagem de erro caso o usuário passe o id errado.... está estourando excessão ao dar um get no item 0 da lista de array
    @GetMapping("/{id}/comics")
    fun getComics(@PathVariable id: Long) = service.getRelatedComics(id)

    @GetMapping("/{id}/events")
    fun getEvents(@PathVariable id: Long) = service.getRelatedEvents(id)

    @GetMapping("/{id}/series")
    fun getSeries(@PathVariable id: Long) = service.getRelatedSeries(id)

    @GetMapping("/{id}/stories")
    fun getStories(@PathVariable id: Long) = service.getRelatedStories(id)


}


