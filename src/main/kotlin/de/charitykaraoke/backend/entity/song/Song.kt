package de.charitykaraoke.backend.entity.song

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "songs")
data class Song(
        @Id @GeneratedValue(
                strategy = GenerationType.IDENTITY) val id: Int = 0,
        var title: String,
        var artist: String,
        var link: String
)