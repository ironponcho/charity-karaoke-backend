package de.charitykaraoke.backend.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity(name = "votes")
data class Vote(
    @Id @GeneratedValue(
        strategy = GenerationType.IDENTITY
    ) val id: Int = 0,
    var percentage: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
    @JsonIgnore
    var recipient: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "karaoke_id", nullable = false)
    @JsonIgnore
    var karaoke: Karaoke

)