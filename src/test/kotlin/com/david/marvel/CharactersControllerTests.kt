package com.david.marvel

import com.david.marvel.model.Character
import com.david.marvel.repository.CharactersRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class CharactersControllerTests {

    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var charactersRepository: CharactersRepository

    @Test
    fun `test find all`() {
        charactersRepository.save(Character(name = "Hulk", description = "Hulk Esmaga"))
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/public/characters"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$").isArray)
                .andExpect(MockMvcResultMatchers.jsonPath("\$[0].id").isNumber)
                .andExpect(MockMvcResultMatchers.jsonPath("\$[0].name").isString)
                .andExpect(MockMvcResultMatchers.jsonPath("\$[0].description").isString)
                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `test find by id`() {
        val character = charactersRepository.save(Character(name = "Hulk", description = "Hulk Esmaga"))

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/public/characters/${character.id}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(character.id))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(character.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.description").value(character.description))
                .andDo(MockMvcResultHandlers.print())

    }

    @Test
    fun `test create character`() {
        val character = Character(name = "Hulk", description = "Hulk Esmaga")
        val json = ObjectMapper().writeValueAsString(character)
        charactersRepository.deleteAll()
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/public/characters")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(character.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.description").value(character.description))
                .andDo(MockMvcResultHandlers.print())

        Assertions.assertFalse(charactersRepository.findAll().isEmpty())

    }

    @Test
    fun `test update character`() {
        val character = charactersRepository
                .save(Character(name = "Hulk", description = "Hulk Esmaga"))
                .copy(name = "Hulk - UPDATED")
        val json = ObjectMapper().writeValueAsString(character)
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/public/characters/${character.id}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(character.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.description").value(character.description))
                .andDo(MockMvcResultHandlers.print())

        val findById = charactersRepository.findById(character.id!!)
        Assertions.assertTrue(findById.isPresent)
        Assertions.assertEquals(character.name, findById.get().name)

    }

    @Test
    fun `test delete character`() {
        val character = charactersRepository.save(Character(name = "Hulk", description = "Hulk Esmaga"))
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/public/characters/${character.id}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())

        val findById = charactersRepository.findById(character.id!!)
        Assertions.assertFalse(findById.isPresent)
    }
}
