package de.charitykaraoke.backend.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity(name = "songs")
data class Song(
    @Id @GeneratedValue(
        strategy = GenerationType.IDENTITY
    ) val id: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "karaoke_id")
    @JsonIgnore
    var karaoke: Karaoke,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @OneToMany(mappedBy = "song" ,fetch = FetchType.LAZY)
    var votes: List<Vote> = emptyList(),

    var title: String,
    var artist: String,
    var link: String,
    var sequence: Int? = 0
)
