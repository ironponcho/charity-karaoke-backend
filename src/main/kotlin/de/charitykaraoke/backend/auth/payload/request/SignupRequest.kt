package de.charitykaraoke.backend.auth.payload.request

import javax.validation.constraints.NotBlank

class SignupRequest(
        var username: @NotBlank String,
        var password: @NotBlank String,
        var karaoke_id: @NotBlank Int
)