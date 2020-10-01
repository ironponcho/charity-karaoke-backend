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

    data class SongRequest(
        var title: String,
        var artist: String,
        var link: String,
        var karaokeId: Int
    )

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_USER')")
    fun createSong(principal: Principal, @RequestBody songRequest: SongRequest): ResponseEntity<String> {

        val user = userRepository.findByUsername(principal.name)

        val karaoke = karaokeRepository.findById(songRequest.karaokeId)

        if (karaoke.isEmpty) {
            return ResponseEntity.badRequest().body("Karaoke not found!")
        }

        songRepository.save(Song(title = songRequest.title, artist = songRequest.artist, link = songRequest.link, karaoke = karaoke.get(), user = user.get()))
        return ResponseEntity.ok().build()
    }
}
