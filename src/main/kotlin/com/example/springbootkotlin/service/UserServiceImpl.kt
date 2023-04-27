package com.example.springbootkotlin.service

import com.example.springbootkotlin.dao.UserRepository
import com.example.springbootkotlin.modal.JwtUserDetails
import com.example.springbootkotlin.modal.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService, OidcUserService() {


    override fun addUser(user: User): User {
        return userRepository.save(user)
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String?): UserDetails {
        val user: User = username?.let { userRepository.findDistinctByName(it) } ?: throw UsernameNotFoundException("User not found")
        return JwtUserDetails(user)
    }




}
