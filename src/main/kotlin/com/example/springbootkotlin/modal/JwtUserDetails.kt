package com.example.springbootkotlin.modal

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


data class JwtUserDetails(private var user: User) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities: MutableSet<SimpleGrantedAuthority> = HashSet()
        authorities.add(SimpleGrantedAuthority(user.role))
        return authorities
    }

    override fun getPassword(): String {
        return this.user.password
    }

    override fun getUsername(): String {
        return this.user.name
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

}
