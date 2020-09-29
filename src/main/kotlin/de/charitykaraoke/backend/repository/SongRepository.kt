package de.charitykaraoke.backend.repository

import de.charitykaraoke.backend.entity.Song
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface SongRepository : JpaRepository<Song, Int>