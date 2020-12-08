package com.aris.booklibraries.demoBookLibraries.services

import com.aris.booklibraries.demoBookLibraries.models.Book
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class TestService {
    @Autowired
    var userService = UserService()
    @Autowired
    var authorService = AuthorService()
    @Autowired
    var bookService = BookService()

    @PostConstruct
    fun postConstruct() {
        bookService.save(Book(id =null, title ="1000 nights",null) )
       // userService.save(User(id= null, email = "giannis@gmail.com", firstName = "giannis", lastName = "kostopoulos") )
     //   authorService.save(Author(id= null, email = "myAuthor@gmail.com", firstName = "firstAuthor", lastName = "akr",books = null) )


//        println( userService.findById(2))
//        var user = userService.findByFirstName("aris")
//        if ( user != null) {
//            println( user )
//        } else {
//            println("sth went wrong")
//        }

    }
}