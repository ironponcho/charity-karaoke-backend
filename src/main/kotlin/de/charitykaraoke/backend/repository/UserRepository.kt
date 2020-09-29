package de.charitykaraoke.backend.repository

import de.charitykaraoke.backend.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface UserRepository : JpaRepository<User, Int> {

    fun findByUsername(username: String): Optional<User>
    fun existsByUsername(username: String): Boolean

}