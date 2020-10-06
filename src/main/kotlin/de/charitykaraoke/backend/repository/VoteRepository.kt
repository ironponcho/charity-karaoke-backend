package de.charitykaraoke.backend.repository

import de.charitykaraoke.backend.entity.Vote
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface VoteRepository : JpaRepository<Vote, Int> {

    fun findByUserIdAndKaraokeIdAndRecipientId(userId: Int, karaokeId: Int, recipientId: Int): Optional<Vote>
}
