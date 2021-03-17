package com.david.marvel.repository

import com.david.marvel.model.Series
import org.springframework.data.jpa.repository.JpaRepository

interface SeriesRepository : JpaRepository<Series, Long>