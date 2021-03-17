package com.david.marvel.repository

import com.david.marvel.model.Comics
import org.springframework.data.jpa.repository.JpaRepository

interface ComicsRepository : JpaRepository<Comics, Long>