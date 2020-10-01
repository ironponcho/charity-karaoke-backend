package de.charitykaraoke.backend.auth.payload.request

import javax.validation.constraints.NotBlank

class LoginRequest(
    var username: @NotBlank String,
    var password: @NotBlank String
)
