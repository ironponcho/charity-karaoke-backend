package de.charitykaraoke.backend.entity.vote

import com.fasterxml.jackson.annotation.JsonIgnore
import de.charitykaraoke.backend.entity.attendee.Attendee
import javax.persistence.*

@Entity(name = "votes")
data class Vote(
        @Id @GeneratedValue(
                strategy = GenerationType.IDENTITY) val id: Int = 0,
        var percentage: Int,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "attendee_id", nullable = false)
        @JsonIgnore
        var givenAttendee: Attendee?

)