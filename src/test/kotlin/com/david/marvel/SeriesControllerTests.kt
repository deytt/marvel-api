package com.david.marvel

import com.david.marvel.model.Series
import com.david.marvel.repository.SeriesRepository
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
class SeriesControllerTests {
    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var seriesRepository: SeriesRepository

    @Test
    fun `test find all`() {
        seriesRepository.save(Series(name = "Hulk", title = "Hulk Esmaga", description = "Hulk Esmaga", modified = "2019-02-03"))
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/public/series"))
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
        val series = seriesRepository.save(Series(name = "Hulk", title = "Hulk Esmaga", description = "Hulk Esmaga", modified = "2019-02-03"))

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/public/series/${series.id}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(series.id))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(series.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.title").value(series.title))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.description").value(series.description))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.modified").value(series.modified))
                .andDo(MockMvcResultHandlers.print())

    }

    @Test
    fun `test create series`() {
        val series = Series(name = "Hulk", title = "Hulk Esmaga", description = "Hulk Esmaga", modified = "2019-02-03")
        val json = ObjectMapper().writeValueAsString(series)
        seriesRepository.deleteAll()
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/public/series")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(series.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.title").value(series.title))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.description").value(series.description))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.modified").value(series.modified))
                .andDo(MockMvcResultHandlers.print())

        Assertions.assertFalse(seriesRepository.findAll().isEmpty())

    }

    @Test
    fun `test update series`() {
        val series = seriesRepository
                .save(Series(name = "Hulk", title = "Hulk Esmaga", description = "Hulk Esmaga", modified = "2019-02-03"))
                .copy(name = "Hulk - UPDATED")
        val json = ObjectMapper().writeValueAsString(series)
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/public/series/${series.id}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(series.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.title").value(series.title))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.description").value(series.description))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.modified").value(series.modified))
                .andDo(MockMvcResultHandlers.print())

        val findById = seriesRepository.findById(series.id!!)
        Assertions.assertTrue(findById.isPresent)
        Assertions.assertEquals(series.name, findById.get().name)

    }

    @Test
    fun `test delete series`() {
        val series = seriesRepository.save(Series(name = "Hulk", title = "Hulk Esmaga", description = "Hulk Esmaga", modified = "2019-02-03"))
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/public/series/${series.id}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())

        val findById = seriesRepository.findById(series.id!!)
        Assertions.assertFalse(findById.isPresent)
    }
}