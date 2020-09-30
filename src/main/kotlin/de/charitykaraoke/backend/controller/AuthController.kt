package de.charitykaraoke.backend.controller


import de.charitykaraoke.backend.auth.JwtUtils
import de.charitykaraoke.backend.auth.KaraokeUserDetails
import de.charitykaraoke.backend.auth.payload.request.LoginRequest
import de.charitykaraoke.backend.auth.payload.request.SignupRequest
import de.charitykaraoke.backend.auth.payload.response.JwtResponse
import de.charitykaraoke.backend.auth.payload.response.MessageResponse
import de.charitykaraoke.backend.entity.User
import de.charitykaraoke.backend.repository.KaraokeRepository
import de.charitykaraoke.backend.repository.RoleRepository
import de.charitykaraoke.backend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController {
    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var karaokeRepository: KaraokeRepository

    @Autowired
    lateinit var encoder: PasswordEncoder

    @Autowired
    lateinit var jwtUtils: JwtUtils

    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: @Valid LoginRequest): ResponseEntity<*> {
        val authentication: Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password))
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils.generateJwtToken(authentication)
        val userDetails: KaraokeUserDetails = authentication.principal as KaraokeUserDetails
        val roles: List<String> = userDetails.authorities.stream()
                .map { item -> item.authority }
                .collect(Collectors.toList())
        return ResponseEntity.ok(JwtResponse(jwt,
                userDetails.id,
                userDetails.username,
                roles))
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody signupRequest: @Valid SignupRequest): ResponseEntity<*> {
        if (userRepository.existsByUsername(signupRequest.username)) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse("Error: Username is already taken!"))
        }

        val optKaraoke = karaokeRepository.findById(signupRequest.karaoke_id)

        return if (optKaraoke.isPresent) {
            userRepository.save(User(username = signupRequest.username, password = encoder.encode(signupRequest.password), karaoke = listOf(optKaraoke.get())))
            ResponseEntity.ok(MessageResponse("User registered successfully!"))
        } else {
            ResponseEntity
                    .badRequest()
                    .body(MessageResponse("Error: Karaoke not found!"))
        }

    }
}
