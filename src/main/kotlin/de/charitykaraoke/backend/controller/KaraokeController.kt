package de.charitykaraoke.backend.controller

import de.charitykaraoke.backend.entity.Karaoke
import de.charitykaraoke.backend.repository.KaraokeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/karaoke")
class KaraokeController(@Autowired private val karaokeRepository: KaraokeRepository) {

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun createKaraoke(@RequestBody karaoke: Karaoke): Karaoke = karaokeRepository.save(karaoke)

    @GetMapping("/{karaokeId}")
    fun getKaraokeById(authentication: Authentication?, @PathVariable karaokeId: Int): ResponseEntity<Karaoke> =

        karaokeRepository.findById(karaokeId).map {
            if (authentication == null || authentication.authorities.stream()
                    .noneMatch { a -> a.authority == "ROLE_ADMIN" }
            ) {
                it.attendees = emptyList()
            }
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())

    @GetMapping()
    fun getAllKaraokes(authentication: Authentication?): List<Karaoke> {

        val karaokes: List<Karaoke> = karaokeRepository.findAll()
        if (authentication == null || authentication.authorities.stream()
                .noneMatch { a -> a.authority == "ROLE_ADMIN" }
        ) {
            return karaokes.onEach { it.attendees = emptyList() }
        }
        return karaokes
    }

}