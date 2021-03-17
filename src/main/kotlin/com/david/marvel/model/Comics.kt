package com.david.marvel.model

import javax.persistence.*

@Table(name = "comics")
@Entity(name = "comics")
data class Comics(
        @Id @GeneratedValue
        var id: Long? = null,

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "character_id")
        var characters : Character? = null,
        @Column(name = "name")
        val name: String,
        @Column(name = "title")
        val title: String,
        @Column(name = "description")
        val description: String,
        @Column(name = "modified")
        val modified: String
)