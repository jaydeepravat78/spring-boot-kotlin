package com.example.springbootkotlin.service

import com.example.springbootkotlin.modal.User
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService{
    fun addUser(user: User): User


}
