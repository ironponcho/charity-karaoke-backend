package de.charitykaraoke.backend.entity.attendee

import de.charitykaraoke.backend.entity.karaoke.Karaoke
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface AttendeeRepository : JpaRepository<Attendee, Int> {

    fun findByKaraoke(karaoke: Karaoke, sort: Sort): List<Attendee>

}