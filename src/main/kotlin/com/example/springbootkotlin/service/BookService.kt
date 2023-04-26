package com.example.springbootkotlin.service

import com.example.springbootkotlin.modal.Book
import org.springframework.stereotype.Service
import java.util.*

interface BookService {
    fun getBookById(id: Long): Optional<Book>
    fun getAllBooks() : List<Book>
    fun addBook(book: Book): Book
    fun updateBook(book: Book): Book
    fun deleteBook(id: Long): Boolean
}
