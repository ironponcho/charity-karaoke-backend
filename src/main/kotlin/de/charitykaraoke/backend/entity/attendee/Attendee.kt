package de.charitykaraoke.backend.entity.attendee

import com.fasterxml.jackson.annotation.JsonIgnore
import de.charitykaraoke.backend.entity.karaoke.Karaoke
import javax.persistence.*

@Entity(name = "attendees")
data class Attendee(
        @Id @GeneratedValue(
                strategy = GenerationType.IDENTITY) val id: Int = 0,
        var name: String,
        var password: String,
        var title: String,
        var artist: String,
        var link: String,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "karaoke_id", nullable = false)
        @JsonIgnore
        var karaoke: Karaoke?
)