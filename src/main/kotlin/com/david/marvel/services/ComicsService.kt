package com.david.marvel.services

import com.david.marvel.model.Comics
import java.util.Optional

interface ComicsService {
    fun create(comics: Comics): Comics
    fun getAll(): List<Comics>
    fun findById(id: Long): Optional<Comics>
    fun update(id: Long, comics: Comics): Optional<Comics>
    fun delete(id: Long)
}