package de.charitykaraoke.backend.controller

import de.charitykaraoke.backend.entity.karaoke.Karaoke
import de.charitykaraoke.backend.entity.karaoke.KaraokeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/karaoke")
class KaraokeController(@Autowired private val karaokeRepository: KaraokeRepository) {

    @PostMapping()
    fun createKaraoke(@RequestBody karaoke: Karaoke): Karaoke = karaokeRepository.save(karaoke)

    @GetMapping("/{karaokeId}")
    fun getKaraokeById(@PathVariable karaokeId: Int): ResponseEntity<Karaoke> =
            karaokeRepository.findById(karaokeId).map {
                ResponseEntity.ok(it)
            }.orElse(ResponseEntity.notFound().build())

    @GetMapping()
    fun getAllKaraokes(): List<Karaoke> = karaokeRepository.findAll()
}