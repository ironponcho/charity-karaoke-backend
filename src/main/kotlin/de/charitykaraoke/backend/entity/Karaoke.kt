package de.charitykaraoke.backend.entity

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity(name = "karaokes")
data class Karaoke(
        @Id @GeneratedValue(
                strategy = GenerationType.IDENTITY) val id: Int = 0,
        @get: NotBlank var name: String,

        var date: OffsetDateTime,
        var expired: Boolean = false,

        @ManyToMany(fetch = FetchType.LAZY, mappedBy = "karaoke")
        var users: List<User>?

)