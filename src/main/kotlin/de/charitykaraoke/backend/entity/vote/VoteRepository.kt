package de.charitykaraoke.backend.entity.vote

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface VoteRepository : JpaRepository<Vote, Int> {

}