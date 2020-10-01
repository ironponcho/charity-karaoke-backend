package de.charitykaraoke.backend.repository

import de.charitykaraoke.backend.entity.Vote
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VoteRepository : JpaRepository<Vote, Int>
