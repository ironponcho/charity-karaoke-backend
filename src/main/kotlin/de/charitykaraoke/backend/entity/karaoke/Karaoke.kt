package de.charitykaraoke.backend.entity.karaoke

import de.charitykaraoke.backend.entity.attendee.Attendee
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity(name = "karaokes")
data class Karaoke(
        @Id @GeneratedValue(
                strategy = GenerationType.IDENTITY) val id: Int = 0,
        @get: NotBlank var name: String,
        var expired: Boolean = false,
        @OneToMany(mappedBy = "karaoke", fetch = FetchType.LAZY,
                cascade = [CascadeType.ALL])
        var attendees: List<Attendee> = listOf()
) {
}