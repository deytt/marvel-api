package com.david.marvel.services

import com.david.marvel.model.Stories
import java.util.*

interface StoriesService {
    fun create(stories: Stories): Stories
    fun getAll(): List<Stories>
    fun findById(id: Long): Optional<Stories>
    fun update(id: Long, stories: Stories): Optional<Stories>
    fun delete(id: Long)
}