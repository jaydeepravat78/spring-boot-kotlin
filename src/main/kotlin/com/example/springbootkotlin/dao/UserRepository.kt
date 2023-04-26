package com.example.springbootkotlin.dao

import com.example.springbootkotlin.modal.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun findDistinctByName(name: String) : User
}
