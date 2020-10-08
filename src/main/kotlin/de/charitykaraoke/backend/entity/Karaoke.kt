package de.charitykaraoke.backend.entity

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.OffsetDateTime
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToMany
import javax.persistence.OneToOne
import javax.validation.constraints.NotBlank

@Entity(name = "karaokes")
data class Karaoke(
    @Id @GeneratedValue(
        strategy = GenerationType.IDENTITY
    ) val id: Int = 0,
    @get: NotBlank var name: String,

    var date: OffsetDateTime,
    var expired: Boolean = false,

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "karaoke")
    var attendees: List<User>? = emptyList(),

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_song_id")
    var currentSong: Song? = null

)
