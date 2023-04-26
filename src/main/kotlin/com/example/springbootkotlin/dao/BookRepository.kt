package com.example.springbootkotlin.dao

import com.example.springbootkotlin.modal.Book
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaRepository

@Repository
interface BookRepository: JpaRepository<Book, Long>{
    fun getBookByAuthor(author: String)
}
