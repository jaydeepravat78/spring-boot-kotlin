package com.example.springbootkotlin.modal

import org.springframework.data.annotation.Id
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Entity
data class User (
    @javax.persistence.Id @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var name: String = "",
    var password: String  = "",
    var email: String = "",
    var role: String = "",
)
