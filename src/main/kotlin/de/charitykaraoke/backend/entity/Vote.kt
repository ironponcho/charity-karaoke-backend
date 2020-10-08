package de.charitykaraoke.backend.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "votes")
data class Vote(
    @Id @GeneratedValue(
        strategy = GenerationType.IDENTITY
    ) val id: Int = 0,
    var percentage: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    var user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", nullable = false)
    @JsonIgnore
    var song: Song,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "karaoke_id", nullable = false)
    @JsonIgnore
    var karaoke: Karaoke

)
