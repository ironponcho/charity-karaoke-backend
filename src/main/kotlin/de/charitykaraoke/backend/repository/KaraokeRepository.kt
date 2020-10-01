package de.charitykaraoke.backend.repository

import de.charitykaraoke.backend.entity.Karaoke
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface KaraokeRepository : JpaRepository<Karaoke, Int>
