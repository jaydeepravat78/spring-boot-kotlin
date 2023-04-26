package com.example.springbootkotlin.service

import com.example.springbootkotlin.dao.BookRepository
import com.example.springbootkotlin.modal.Book
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
class BookServiceImpl(
    @Autowired
    val bookRepository: BookRepository
) : BookService{

    override fun getBookById(id: Long): Optional<Book> {
        println(bookRepository.getBookByAuthor("abc"))
        return bookRepository.findById(id)
    }

    override fun getAllBooks() :List<Book>{
        return bookRepository.findAll()
    }

    override fun addBook(book: Book) : Book{
        return bookRepository.save(book)
    }

    override fun updateBook(book: Book) : Book{
        return bookRepository.save(book)
    }

    override fun deleteBook(id: Long) :Boolean{
        return run {
            bookRepository.deleteById(id)
            true;
        }
    }
}
