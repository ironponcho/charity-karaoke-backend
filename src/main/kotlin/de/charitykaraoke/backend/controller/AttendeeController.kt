package de.charitykaraoke.backend.controller

import de.charitykaraoke.backend.entity.attendee.Attendee
import de.charitykaraoke.backend.entity.attendee.AttendeeRepository
import de.charitykaraoke.backend.entity.karaoke.KaraokeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping()
class AttendeeController(@Autowired private val attendeeRepository: AttendeeRepository, @Autowired private val karaokeRepository: KaraokeRepository) {

    @PostMapping("api/karaoke/{karaokeId}/attendee")
    fun createAttendee(@PathVariable(value = "karaokeId") karaokeId: Int,
                       @RequestBody attendee: Attendee): ResponseEntity<Attendee> =
            karaokeRepository.findById(karaokeId).map { karaoke ->
                attendee.karaoke = karaoke
                attendeeRepository.save(attendee)
                ResponseEntity.ok().build<Attendee>()
            }.orElse(ResponseEntity.notFound().build<Attendee>())
    /*
    @PostMapping("api/karaoke/karaoke/login")
    fun login(@RequestBody login: Login): User
*/

}

data class Login (
    val username: String,
    val password: String,
    val karaokeId: String
)

data class User (
    val name: String,
    val id: String,
    val karaokeId: String,
    val isAdmin: Boolean,
    val token: String
)

