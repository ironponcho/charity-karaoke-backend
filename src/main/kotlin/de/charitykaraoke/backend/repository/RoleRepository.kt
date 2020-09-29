package de.charitykaraoke.backend.repository

import de.charitykaraoke.backend.entity.ERole
import de.charitykaraoke.backend.entity.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface RoleRepository : JpaRepository<Role, Int> {
//    fun findByName(name: ERole): Optional<Role>
//    fun existsByName(name: ERole): Boolean
    fun findByName(name: String): Optional<Role>
    fun existsByName(name: String): Boolean
}