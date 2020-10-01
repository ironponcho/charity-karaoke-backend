package de.charitykaraoke.backend.auth.payload.response

class JwtResponse(var accessToken: String, var id: Int, var name: String, val roles: List<String>) {
    var tokenType = "Bearer"
}
