package com.david.marvel;


import com.david.marvel.model.Comics
import com.david.marvel.repository.ComicsRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class ComicsControllerTests {

    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var comicssRepository: ComicsRepository

    @Test
    fun `test find all`() {
        comicssRepository.save(Comics(name = "Hulk", title = "Hulk Esmaga", description = "Hulk Esmaga", modified = "2019-02-03"))
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/public/comics"))
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
        val comics = comicssRepository.save(Comics(name = "Hulk", title = "Hulk Esmaga", description = "Hulk Esmaga", modified = "2019-02-03"))

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/public/comics/${comics.id}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(comics.id))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(comics.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.title").value(comics.title))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.description").value(comics.description))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.modified").value(comics.modified))
                .andDo(MockMvcResultHandlers.print())

    }

    @Test
    fun `test create comics`() {
        val comics = Comics(name = "Hulk", title = "Hulk Esmaga", description = "Hulk Esmaga", modified = "2019-02-03")
        val json = ObjectMapper().writeValueAsString(comics)
        comicssRepository.deleteAll()
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/public/comics")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(comics.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.title").value(comics.title))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.description").value(comics.description))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.modified").value(comics.modified))
                .andDo(MockMvcResultHandlers.print())

        Assertions.assertFalse(comicssRepository.findAll().isEmpty())

    }

    @Test
    fun `test update comics`() {
        val comics = comicssRepository
                .save(Comics(name = "Hulk", title = "Hulk Esmaga", description = "Hulk Esmaga", modified = "2019-02-03"))
                .copy(name = "Hulk - UPDATED")
        val json = ObjectMapper().writeValueAsString(comics)
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/public/comics/${comics.id}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(comics.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.title").value(comics.title))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.description").value(comics.description))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.modified").value(comics.modified))
                .andDo(MockMvcResultHandlers.print())

        val findById = comicssRepository.findById(comics.id!!)
        Assertions.assertTrue(findById.isPresent)
        Assertions.assertEquals(comics.name, findById.get().name)

    }

    @Test
    fun `test delete comics`() {
        val comics = comicssRepository.save(Comics(name = "Hulk", title = "Hulk Esmaga", description = "Hulk Esmaga", modified = "2019-02-03"))
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/public/comics/${comics.id}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())

        val findById = comicssRepository.findById(comics.id!!)
        Assertions.assertFalse(findById.isPresent)
    }
}
