package de.charitykaraoke.backend.entity.attendee

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import de.charitykaraoke.backend.entity.karaoke.Karaoke
import de.charitykaraoke.backend.entity.song.Song
import de.charitykaraoke.backend.entity.vote.Vote
import javax.persistence.*

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "attendees")
data class Attendee(
        @Id @GeneratedValue(
                strategy = GenerationType.IDENTITY) val id: Int = 0,
        var name: String,
        var password: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "karaoke_id", nullable = false)
        @JsonIgnore
        var karaoke: Karaoke?,

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "song_id", nullable = false)
        var song: Song?,

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "vote_id", nullable = false)
        var vote: Vote?,

        @OneToMany(mappedBy = "givenAttendee", fetch = FetchType.LAZY)
        var givenVotes: List<Vote>?
)
