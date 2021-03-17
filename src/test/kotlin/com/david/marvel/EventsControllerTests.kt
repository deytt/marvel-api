package com.david.marvel

import com.david.marvel.model.Events
import com.david.marvel.repository.EventsRepository
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
class EventsControllerTests {
    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var eventsRepository: EventsRepository

    @Test
    fun `test find all`() {
        eventsRepository.save(Events(name = "Hulk", title = "Hulk Esmaga", description = "Hulk Esmaga", modified = "2019-02-03"))
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/public/events"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$").isArray)
                .andExpect(MockMvcResultMatchers.jsonPath("\$[0].id").isNumber)
                .andExpect(MockMvcResultMatchers.jsonPath("\$[0].name").isString)
                .andExpect(MockMvcResultMatchers.jsonPath("\$[0].title").isString)
                .andExpect(MockMvcResultMatchers.jsonPath("\$[0].description").isString)
                .andExpect(MockMvcResultMatchers.jsonPath("\$[0].modified").isString)
                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `test find by id`() {
        val events = eventsRepository.save(Events(name = "Hulk", title = "Hulk Esmaga", description = "Hulk Esmaga", modified = "2019-02-03"))

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/public/events/${events.id}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(events.id))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(events.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.title").value(events.title))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.description").value(events.description))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.modified").value(events.modified))
                .andDo(MockMvcResultHandlers.print())

    }

    @Test
    fun `test create events`() {
        val events = Events(name = "Hulk", title = "Hulk Esmaga", description = "Hulk Esmaga", modified = "2019-02-03")
        val json = ObjectMapper().writeValueAsString(events)
        eventsRepository.deleteAll()
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/public/events")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(events.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.title").value(events.title))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.description").value(events.description))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.modified").value(events.modified))
                .andDo(MockMvcResultHandlers.print())

        Assertions.assertFalse(eventsRepository.findAll().isEmpty())

    }

    @Test
    fun `test update events`() {
        val events = eventsRepository
                .save(Events(name = "Hulk", title = "Hulk Esmaga", description = "Hulk Esmaga", modified = "2019-02-03"))
                .copy(name = "Hulk - UPDATED")
        val json = ObjectMapper().writeValueAsString(events)
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/public/events/${events.id}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(events.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.title").value(events.title))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.description").value(events.description))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.modified").value(events.modified))
                .andDo(MockMvcResultHandlers.print())

        val findById = eventsRepository.findById(events.id!!)
        Assertions.assertTrue(findById.isPresent)
        Assertions.assertEquals(events.name, findById.get().name)

    }

    @Test
    fun `test delete events`() {
        val events = eventsRepository.save(Events(name = "Hulk", title = "Hulk Esmaga", description = "Hulk Esmaga", modified = "2019-02-03"))
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/public/events/${events.id}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())

        val findById = eventsRepository.findById(events.id!!)
        Assertions.assertFalse(findById.isPresent)
    }
}