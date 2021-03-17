package com.david.marvel.repository

import com.david.marvel.model.Stories
import org.springframework.data.jpa.repository.JpaRepository

interface StoriesRepository : JpaRepository<Stories, Long>