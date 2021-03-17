package com.david.marvel.services

import com.david.marvel.model.Series
import java.util.*

interface SeriesService {
    fun create(series: Series): Series
    fun getAll(): List<Series>
    fun findById(id: Long): Optional<Series>
    fun update(id: Long, series: Series): Optional<Series>
    fun delete(id: Long)
}