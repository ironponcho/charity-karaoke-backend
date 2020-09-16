package de.charitykaraoke.backend.entity.karaoke

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface KaraokeRepository : JpaRepository<Karaoke, Int> {

}