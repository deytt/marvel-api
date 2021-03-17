package com.david.marvel.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Table(name = "character")
@Entity(name = "character")
data class Character (
        @Id @GeneratedValue
        var id: Long? = null,

        @OneToMany(mappedBy = "characters", cascade = [CascadeType.ALL])
        @JsonIgnore
        var comics: List<Comics> = emptyList(),

        @OneToMany(mappedBy = "characters", cascade = [CascadeType.ALL])
        @JsonIgnore
        var events: List<Events> = emptyList(),

        @OneToMany(mappedBy = "characters", cascade = [CascadeType.ALL])
        @JsonIgnore
        var series: List<Series> = emptyList(),

        @OneToMany(mappedBy = "characters", cascade = [CascadeType.ALL])
        @JsonIgnore
        var stories: List<Stories> = emptyList(),

        val name: String,
        val description: String,
        val modified: String
)