package com.david.marvel.repository

import com.david.marvel.model.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CharactersRepository : JpaRepository<Character, Long> {
    @Query(value = "select cm from comics cm where cm.characters.id = ?1")
    fun findComicsByCharacterId(id: Long) : List<Array<Comics>>

    @Query(value = "select ev from events ev where ev.characters.id = ?1")
    fun findEventsByCharacterId(id: Long) : List<Array<Events>>

    @Query(value = "select se from series se where se.characters.id = ?1")
    fun findSeriesByCharacterId(id: Long) : List<Array<Series>>

    @Query(value = "select st from stories st where st.characters.id = ?1")
    fun findStoriesByCharacterId(id: Long) : List<Array<Stories>>
}