package de.charitykaraoke.backend.controller

import de.charitykaraoke.backend.entity.Song
import de.charitykaraoke.backend.repository.KaraokeRepository
import de.charitykaraoke.backend.repository.SongRepository
import de.charitykaraoke.backend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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

    @GetMapping("/{karaokeId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    fun getSongsForKaraoke(authentication: Authentication, @PathVariable karaokeId: Int): ResponseEntity<List<Song>> {

        val songs = songRepository.findByKaraokeIdOrderBySequenceAsc(karaokeId)

        if (authentication.authorities.stream()
            .noneMatch { a -> a.authority == "ROLE_ADMIN" }
        ) {
            songs.onEach { song ->
                run {
                    val vote = song.votes.find { it.user.username == authentication.name }

                    if (vote != null) {
                        song.votes = listOf(vote)
                    } else {
                        song.votes = emptyList()
                    }
                }
            }
        }

        return ResponseEntity.ok().body(songs)
    }

    @GetMapping("/shuffle/{karaokeId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun shuffleSequences(principal: Principal, @PathVariable karaokeId: Int): ResponseEntity<List<Song>> {
        val songs = songRepository.findByKaraokeIdOrderBySequenceAsc(karaokeId)

        songs.shuffled()

        for (i in songs.indices) {
            songs[i].sequence = i
        }

        songRepository.saveAll(songs)

        return ResponseEntity.ok().body(songs)
    }

//    data class Sequence (
//        var songId: Int,
//        var sequence: Int
//        )
//
//    data class SequenceRequest(
//        var karaokeId: Int,
//        var songs: List<Sequence>
//    )
//
//    @PostMapping("/sequence")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    fun setSequences(principal: Principal, @RequestBody request: SequenceRequest): ResponseEntity<List<Song>> {
//        val songs = songRepository.findByKaraokeId(request.karaokeId)
//
//        songRepository.saveAll(songs)
//
//        return ResponseEntity.ok().body(songs)
//    }
}
