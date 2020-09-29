package de.charitykaraoke.backend.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "users")
data class User(
        @Id @GeneratedValue(
                strategy = GenerationType.IDENTITY) val id: Int = 0,
        var username: String,
        var password: String,


        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "user_roles",
                joinColumns = [JoinColumn(name = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "role_id")])

        //Todo: Map enum to database representation
        var roles: List<Role> = listOf(Role(2, "ROLE_USER")),

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "user_karaokes",
                joinColumns = [JoinColumn(name = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "karaoke_id")])
        var karaoke: List<Karaoke>?

)
