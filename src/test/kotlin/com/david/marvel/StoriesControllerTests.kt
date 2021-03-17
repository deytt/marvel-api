package com.david.marvel

import com.david.marvel.model.Stories
import com.david.marvel.repository.StoriesRepository
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
class StoriesControllerTests {
    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var storiesRepository: StoriesRepository

    @Test
    fun `test find all`() {
        storiesRepository.save(Stories(name = "Hulk", title = "Hulk Esmaga", description = "Hulk Esmaga", modified = "2019-02-03"))
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/public/stories"))
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
        val stories = storiesRepository.save(Stories(name = "Hulk", title = "Hulk Esmaga", description = "Hulk Esmaga", modified = "2019-02-03"))

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/public/stories/${stories.id}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(stories.id))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(stories.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.title").value(stories.title))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.description").value(stories.description))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.modified").value(stories.modified))
                .andDo(MockMvcResultHandlers.print())

    }

    @Test
    fun `test create stories`() {
        val stories = Stories(name = "Hulk", title = "Hulk Esmaga", description = "Hulk Esmaga", modified = "2019-02-03")
        val json = ObjectMapper().writeValueAsString(stories)
        storiesRepository.deleteAll()
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/public/stories")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(stories.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.title").value(stories.title))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.description").value(stories.description))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.modified").value(stories.modified))
                .andDo(MockMvcResultHandlers.print())

        Assertions.assertFalse(storiesRepository.findAll().isEmpty())

    }

    @Test
    fun `test update stories`() {
        val stories = storiesRepository
                .save(Stories(name = "Hulk", title = "Hulk Esmaga", description = "Hulk Esmaga", modified = "2019-02-03"))
                .copy(name = "Hulk - UPDATED")
        val json = ObjectMapper().writeValueAsString(stories)
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/public/stories/${stories.id}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(stories.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.title").value(stories.title))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.description").value(stories.description))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.modified").value(stories.modified))
                .andDo(MockMvcResultHandlers.print())

        val findById = storiesRepository.findById(stories.id!!)
        Assertions.assertTrue(findById.isPresent)
        Assertions.assertEquals(stories.name, findById.get().name)

    }

    @Test
    fun `test delete stories`() {
        val stories = storiesRepository.save(Stories(name = "Hulk", title = "Hulk Esmaga", description = "Hulk Esmaga", modified = "2019-02-03"))
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/public/stories/${stories.id}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())

        val findById = storiesRepository.findById(stories.id!!)
        Assertions.assertFalse(findById.isPresent)
    }
}