package com.aris.booklibraries.demoBookLibraries.services

import com.aris.booklibraries.demoBookLibraries.models.*
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
    @Autowired
    var cityService = CityService()
    @Autowired
    var libraryService = LibraryService()
    @Autowired
    var hasBookService = HasBookService()

    @PostConstruct
    fun postConstruct() {
       // init()
        //println( hasBookService.getQuantity(1,2))
    }

    fun init() {
        userService.save(User(userId= null, email = "giannis@gmail.com", firstName = "giannis", lastName = "kostopoulos"))
        userService.save(User(userId= null, email = "nikos@gmail.com", firstName = "nikos", lastName = "papadopoulos"))
        userService.save(User(userId= null, email = "elina@gmail.com", firstName = "elina", lastName = "oikonomou"))

        authorService.save(Author(authorId= null, email = "petros@gmail.com", firstName = "petros", lastName = "petrou") )
        authorService.save(Author(authorId= null, email = "nikoleta@gmail.com", firstName = "nikoleta", lastName = "nikoletou") )
        authorService.save(Author(authorId= null, email = "bunny@gmail.com", firstName = "bunny", lastName = "funny") )

        bookService.addBook(Book(null,"avatar",null),authorService.findById(1)!!)
        bookService.addBook(Book(null,"star wars",null),authorService.findById(1)!!)
        bookService.addBook(Book(null,"star wars 2",null),authorService.findById(1)!!)
        bookService.addBook(Book(null,"harry potter",null),authorService.findById(2)!!)
        bookService.addBook(Book(null,"harry potter 2",null),authorService.findById(2)!!)
        bookService.addBook(Book(null,"harry potter 3",null),authorService.findById(2)!!)
        bookService.addBook(Book(null,"harry potter 4",null),authorService.findById(2)!!)

        cityService.cityRepository?.save(City(null,"Thessaloniki"))
        cityService.cityRepository?.save(City(null,"Athens"))
        cityService.cityRepository?.save(City(null,"Larissa"))

        libraryService.addLibrary(Library(null,"Anagnostirio Apth",null,
        ),cityService.cityRepository?.findById(1)!!.orElse(null))
        libraryService.addLibrary(Library(null,"Dimosia Vivliothiki",null,
        ),cityService.cityRepository?.findById(1)!!.orElse(null))
        libraryService.addLibrary(Library(null,"Vivliothiki Athinas",null,
        ),cityService.cityRepository?.findById(2)!!.orElse(null))
        libraryService.addLibrary(Library(null,"Idiotiki Vivliothiki Athinas",null,
        ),cityService.cityRepository?.findById(2)!!.orElse(null))
        libraryService.addLibrary(Library(null,"Vivliothiki Larisas",null,
        ),cityService.cityRepository?.findById(3)!!.orElse(null))

        hasBookService.addBook(1,1,10)
        hasBookService.addBook(1,2,5)
        hasBookService.addBook(2,3,10)
        hasBookService.addBook(2,4,10)
        hasBookService.addBook(3,5,10)
        hasBookService.addBook(3,6,10)
        hasBookService.addBook(4,7,10)
        hasBookService.addBook(4,1,10)
    }
}