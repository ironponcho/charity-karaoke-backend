package de.charitykaraoke.backend.controller

import de.charitykaraoke.backend.repository.KaraokeRepository
import de.charitykaraoke.backend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping()
class AttendeeController(@Autowired private val userRepository: UserRepository, @Autowired private val karaokeRepository: KaraokeRepository) {

//    @PostMapping("/karaoke/{karaokeId}/attendee")
//    fun createAttendee(@PathVariable(value = "karaokeId") karaokeId: Int,
//                       @RequestBody user: User): ResponseEntity<User> =
//            karaokeRepository.findById(karaokeId).map { karaoke ->
//                user.karaoke = karaoke
//                userRepository.save(user)
//                ResponseEntity.ok().build<User>()
//            }.orElse(ResponseEntity.notFound().build<User>())
}
