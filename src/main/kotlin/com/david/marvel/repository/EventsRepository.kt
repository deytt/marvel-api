package com.david.marvel.repository

import com.david.marvel.model.Events
import org.springframework.data.jpa.repository.JpaRepository

interface EventsRepository : JpaRepository<Events, Long>