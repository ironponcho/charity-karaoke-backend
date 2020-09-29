package de.charitykaraoke.backend.entity

import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity(name = "karaokes")
data class Karaoke(
        @Id @GeneratedValue(
                strategy = GenerationType.IDENTITY) val id: Int = 0,
        @get: NotBlank var name: String,
        var date: LocalDateTime,
        var expired: Boolean = false,

        @ManyToMany(fetch = FetchType.LAZY, mappedBy = "karaoke")
        var users: List<User>?

)