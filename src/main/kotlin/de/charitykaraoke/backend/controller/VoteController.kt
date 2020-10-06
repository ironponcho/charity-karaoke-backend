package de.charitykaraoke.backend.controller

import de.charitykaraoke.backend.entity.Vote
import de.charitykaraoke.backend.repository.KaraokeRepository
import de.charitykaraoke.backend.repository.UserRepository
import de.charitykaraoke.backend.repository.VoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/vote")
class VoteController(
    @Autowired private val voteRepository: VoteRepository,
    @Autowired private val karaokeRepository: KaraokeRepository,
    @Autowired private val userRepository: UserRepository
) {

    data class VoteRequest(
        var percentage: Int,
        var recipientId: Int,
        var karaokeId: Int
    )

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_USER')")
    fun createVote(principal: Principal, @RequestBody voteRequest: VoteRequest): ResponseEntity<String> {

        val user = userRepository.findByUsername(principal.name).get()

        val karaoke = karaokeRepository.findById(voteRequest.karaokeId)

        if (karaoke.isEmpty) {
            return ResponseEntity.badRequest().body("Karaoke not found!")
        }

        val recipient = userRepository.findById(voteRequest.recipientId)

        if (recipient.isEmpty) {
            return ResponseEntity.badRequest().body("Recipient not found!")
        }

        val voted = voteRepository.findByUserIdAndKaraokeIdAndRecipientId(user.id, voteRequest.karaokeId, voteRequest.recipientId)

        if (voted.isPresent) {
            val vote = voted.get()
            vote.percentage = voteRequest.percentage
            voteRepository.save(vote)
        } else {
            voteRepository.save(Vote(percentage = voteRequest.percentage, recipient = recipient.get(), user = user, karaoke = karaoke.get()))
        }
        return ResponseEntity.ok().build()
    }
}
