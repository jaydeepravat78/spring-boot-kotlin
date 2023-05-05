package com.example.springbootkotlin.controller

import com.example.springbootkotlin.modal.Book
import com.example.springbootkotlin.modal.JwtRequest
import com.example.springbootkotlin.modal.JwtResponse
import com.example.springbootkotlin.modal.User
import com.example.springbootkotlin.service.BookService
import com.example.springbootkotlin.service.UserService
import com.example.springbootkotlin.util.JwtUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.transaction.Transactional


@RestController
@Transactional
class BookController (
    private val bookService: BookService,
    private val userService: UserService,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil
){

    @PostMapping("/signup")
    fun signIn(@RequestBody user: User): User? {
        println("user login with ${user.name}")
        user.password = passwordEncoder.encode(user.password)
        return userService.addUser(user)
    }

    @PostMapping("/signin")
    @Throws(Exception::class)
    fun signIn(@RequestBody request: JwtRequest): ResponseEntity<*>? {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.userName, request.password))
        } catch (e: BadCredentialsException) {
            return ResponseEntity.ok<Any>("User not found!$e")
        }
        val userDetails = userService.loadUserByUsername(request.userName)
        val token: String? = jwtUtil.generateToken(userDetails)
        val response = token?.let { JwtResponse(it) }
        return ResponseEntity.ok<Any>(response)
    }

    @GetMapping("/")
    fun getAllBooks(): ResponseEntity<Any> {
        return ResponseEntity.ok(bookService.getAllBooks())
    }
    @GetMapping("/{id}")
    fun getBookById(@PathVariable  id: Long): ResponseEntity<Any> {
        return ResponseEntity.ok(bookService.getBookById(id))
    }
    @PostMapping("/")
    fun saveBook(@Validated @RequestBody book: Book): ResponseEntity<Any> {
        return ResponseEntity.ok(bookService.addBook(book))
    }
    @PutMapping("/")
    fun updateBook(@RequestBody book: Book) :  ResponseEntity<Any> {
        return ResponseEntity.ok(bookService.updateBook(book))
    }
    @DeleteMapping("/{id}")
    fun deleteBook(@PathVariable id: Long): Any {
        bookService.getBookById(id).let {
            bookService.deleteBook(id)
            return ResponseEntity.ok("Book deleted successfully")
        }
    }
}
