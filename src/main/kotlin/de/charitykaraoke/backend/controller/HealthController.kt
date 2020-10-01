package de.charitykaraoke.backend.controller

import de.charitykaraoke.backend.auth.payload.response.MessageResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class HealthController {

    @GetMapping("/health")
    fun live(principal: Principal?): ResponseEntity<MessageResponse> {
        return ResponseEntity.ok(MessageResponse("User: " + principal?.name))
    }
}
