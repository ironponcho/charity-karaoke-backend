package de.charitykaraoke.backend.entity.karaoke

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity(name = "karaokes")
data class Karaoke(
        @Id @GeneratedValue(
                strategy = GenerationType.IDENTITY) val id: Int = 0,
        @get: NotBlank val name: String = "",
        val expired: Boolean = false
) {
}