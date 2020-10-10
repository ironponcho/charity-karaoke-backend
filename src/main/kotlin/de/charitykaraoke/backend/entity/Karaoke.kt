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
    var attendees: List<User> = emptyList(),

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_song_id")
    var currentSong: Song? = null

) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Karaoke

        if (id != other.id) return false
        if (name != other.name) return false
        if (date != other.date) return false
        if (expired != other.expired) return false
        if (attendees != other.attendees) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + expired.hashCode()
        result = 31 * result + (attendees.hashCode() ?: 0)
        return result
    }
}
