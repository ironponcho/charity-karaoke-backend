package de.charitykaraoke.backend.auth

import com.fasterxml.jackson.annotation.JsonIgnore
import de.charitykaraoke.backend.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

class KaraokeUserDetails(
    val id: Int,
    private val name: String,
    @field:JsonIgnore private val password: String,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return name
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val user = other as KaraokeUserDetails
        return id == user.id
    }

    companion object {
        private const val serialVersionUID = 1L
        fun build(user: User): KaraokeUserDetails {
            val authorities: List<GrantedAuthority> = user.roles.stream()
                .map { role -> SimpleGrantedAuthority(role.name) }
                .collect(Collectors.toList())
            return KaraokeUserDetails(
                user.id,
                user.username,
                user.password,
                authorities
            )
        }
    }
}
