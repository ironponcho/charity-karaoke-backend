package de.charitykaraoke.backend.seed

import de.charitykaraoke.backend.entity.ERole
import de.charitykaraoke.backend.entity.Role
import de.charitykaraoke.backend.entity.User
import de.charitykaraoke.backend.repository.RoleRepository
import de.charitykaraoke.backend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class DatabaseSeeder {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var encoder: PasswordEncoder


    @EventListener(ApplicationReadyEvent::class)
    fun seed() {
        seedRoleTable()
        seedAdminUserTable()
    }

    private fun seedRoleTable() {
        if (!roleRepository.existsByName(ERole.ADMIN.toString())) {
            roleRepository.save(Role(id = 1, name = ERole.ADMIN.toString()))
        }
        if (!roleRepository.existsByName(ERole.USER.toString())) {
            roleRepository.save(Role(id = 2, name = ERole.USER.toString()))
        }
    }

    private fun seedAdminUserTable() {
        if (!userRepository.existsByUsername("admin")) {
            userRepository.save(
                User(
                    username = "admin",
                    password = encoder.encode("password"),
                    roles = listOf(Role(1, "ADMIN"), Role(2, "USER"))
                )
            )
        }
    }

}