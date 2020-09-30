package de.charitykaraoke.backend.controller

import de.charitykaraoke.backend.entity.Song
import de.charitykaraoke.backend.repository.KaraokeRepository
import de.charitykaraoke.backend.repository.SongRepository
import de.charitykaraoke.backend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal


@RestController
@RequestMapping("/song")
class SongController(
    @Autowired private val songRepository: SongRepository,
    @Autowired private val karaokeRepository: KaraokeRepository,
    @Autowired private val userRepository: UserRepository
) {

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    fun createAttendee(principal: Principal, @RequestBody song: Song): ResponseEntity<Song> {

        val user = userRepository.findByUsername(principal.name).get()
        song.user = user
        song.karaoke = user.karaoke[0]
        songRepository.save(song)
        return ResponseEntity.ok().build()
    }
}
