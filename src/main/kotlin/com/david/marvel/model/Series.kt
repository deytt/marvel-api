package com.david.marvel.model

import javax.persistence.*

@Table(name = "series")
@Entity(name = "series")
data class Series(
        @Id @GeneratedValue
        var id: Long? = null,

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "character_id")
        var characters : Character? = null,
        val name: String,
        val title: String,
        val description: String,
        val modified: String
)