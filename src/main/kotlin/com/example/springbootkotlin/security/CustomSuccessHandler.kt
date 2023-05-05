package com.example.springbootkotlin.security

import com.example.springbootkotlin.dao.UserRepository
import com.example.springbootkotlin.modal.User
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class CustomSuccessHandler (private val userRepository: UserRepository): AuthenticationSuccessHandler{
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        if(authentication?.principal is DefaultOAuth2User) {
            val userDetails = authentication.principal as DefaultOAuth2User
            val userName = userDetails.attributes["name"].toString()
            val userEmail = userDetails.attributes["email"].toString()
            val user: User? = userRepository.findDistinctByName(userName)
            if(user == null) {
                val userData = User()
                userData.name = userName
                userData.email = userEmail
                userData.role = "user"
                userRepository.save(userData)
            }
        }
        response?.sendRedirect("/")
    }
}
