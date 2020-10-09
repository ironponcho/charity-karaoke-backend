package de.charitykaraoke.backend.controller

import de.charitykaraoke.backend.entity.Karaoke
import de.charitykaraoke.backend.entity.Song
import de.charitykaraoke.backend.repository.KaraokeRepository
import de.charitykaraoke.backend.repository.SongRepository
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
@RequestMapping("/karaoke")
class KaraokeController() {

    @Autowired
    lateinit var karaokeRepository: KaraokeRepository

    @Autowired
    lateinit var songRepository: SongRepository

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun createKaraoke(@RequestBody karaoke: Karaoke): Karaoke = karaokeRepository.save(karaoke)

    data class KaraokeControlRequest(
        val karaokeId: Int,
        val songId: Int
    )

    @PostMapping("/start")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun startKaraoke(@RequestBody pcRequest: KaraokeControlRequest): ResponseEntity<*> =
        karaokeRepository.findById(pcRequest.karaokeId).map { karaoke ->

            if (karaoke.currentSong != null) {
                return@map ResponseEntity.badRequest().body("Karaoke already started!")
            }

            val songs = songRepository.findByKaraokeIdOrderBySequenceAsc(pcRequest.karaokeId)

            if (songs.isEmpty()) {
                return@map ResponseEntity.badRequest().body("Karaoke has no Songs!")
            }

            val firstSong = songs[0]

            if (firstSong.id != pcRequest.songId) {
                return@map ResponseEntity.badRequest().body("Song id is not matching with given sequence!")
            }

            karaoke.currentSong = firstSong
            karaokeRepository.save(karaoke)

            ResponseEntity.ok().body(firstSong)
        }.orElse(ResponseEntity.badRequest().body("Karaoke not found!"))

    @PostMapping("/next")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun nextPerformer(@RequestBody pcRequest: KaraokeControlRequest): ResponseEntity<*> =
        karaokeRepository.findById(pcRequest.karaokeId).map { karaoke ->

            val songs = songRepository.findByKaraokeIdOrderBySequenceAsc(pcRequest.karaokeId)

            val nextSong = songs[songs.indexOf(karaoke.currentSong) + 1]

            if (nextSong.id != pcRequest.songId) {
                return@map ResponseEntity.badRequest().body("Song id is not matching with given sequence!")
            }

            karaoke.currentSong = nextSong
            karaokeRepository.save(karaoke)

            ResponseEntity.ok().body(nextSong)
        }.orElse(ResponseEntity.badRequest().body("Karaoke not found!"))

    @PostMapping("/previous")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun previousPerformer(@RequestBody pcRequest: KaraokeControlRequest): ResponseEntity<*> =
        karaokeRepository.findById(pcRequest.karaokeId).map { karaoke ->

            val songs = songRepository.findByKaraokeIdOrderBySequenceAsc(pcRequest.karaokeId)

            val previousSong = songs[songs.indexOf(karaoke.currentSong) - 1]

            if (previousSong.id != pcRequest.songId) {
                return@map ResponseEntity.badRequest().body("Song id is not matching with given sequence!")
            }

            karaoke.currentSong = previousSong
            karaokeRepository.save(karaoke)

            ResponseEntity.ok().body(previousSong)
        }.orElse(ResponseEntity.badRequest().body("Karaoke not found!"))

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


    @GetMapping("/currentSong/{karaokeId}")
    fun getCurrentSong(@PathVariable karaokeId: Int) : ResponseEntity<*> {

        val karaoke = karaokeRepository.findById(karaokeId)

        return if (karaoke.isEmpty) {
            ResponseEntity.notFound().build<Unit>()
        } else {
            ResponseEntity.ok().body(karaoke.get().currentSong?.id)
        }
    }

    @GetMapping("/{karaokeId}")
    fun getKaraokeById(authentication: Authentication?, @PathVariable karaokeId: Int): ResponseEntity<Karaoke> =

        karaokeRepository.findById(karaokeId).map {
            if (authentication == null || authentication.authorities.stream()
                    .noneMatch { a -> a.authority == "ROLE_ADMIN" }
            ) {
                it.attendees = emptyList()
                it.currentSong = null
            }
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())


    @GetMapping()
    fun getAllKaraokes(authentication: Authentication?): List<Karaoke> {

        val karaokes: List<Karaoke> = karaokeRepository.findAll()
        if (authentication == null || authentication.authorities.stream()
            .noneMatch { a -> a.authority == "ROLE_ADMIN" }
        ) {
            return karaokes.onEach { it.attendees = emptyList(); it.currentSong = null }
        }
        return karaokes
    }
}
