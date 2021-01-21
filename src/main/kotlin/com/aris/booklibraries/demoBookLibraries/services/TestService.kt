package com.aris.booklibraries.demoBookLibraries.services

import com.aris.booklibraries.demoBookLibraries.executors.AccountExecutor
import com.aris.booklibraries.demoBookLibraries.models.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class TestService {
//    @Autowired
//    lateinit var userServiceOld: UserServiceOld
    @Autowired
    lateinit var authorService :AuthorService
    @Autowired
    lateinit var bookService: BookService
    @Autowired
    lateinit var cityService: CityService
    @Autowired
    lateinit var libraryService: LibraryService
    @Autowired
    lateinit var hasBookService: HasBookService
    @Autowired
    lateinit var librarianServiceOld: LibrarianServiceOld
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var accountService: AccountService
    @Autowired
    lateinit var librarianService: LibrarianService

    @PostConstruct
    fun postConstruct() {
       // init()
    }

    fun init() {
        val encoder = BCryptPasswordEncoder(10)

        val acc1 = accountService.save( Account(null,"aris",encoder.encode("pass"),1,Role.USER.value))
        val acc2 = accountService.save( Account(null,"bill",encoder.encode("pass"),1,Role.USER.value))
        val acc3 = accountService.save( Account(null,"nick",encoder.encode("pass"),1,Role.USER.value))
        val acc4 = accountService.save( Account(null,"lib",encoder.encode("pass"),1,Role.LIBRARIAN.value))

        userService.save(User(null,"Aris", "Tsach", acc1))
        userService.save(User(null,"Bill", "Pap", acc2))
        userService.save(User(null,"Nick", "Oik", acc3))

//        userServiceOld.save(UserOld(userId= null, email = "giannis@gmail.com","123", firstName = "giannis", lastName = "kostopoulos"))
//        userServiceOld.save(UserOld(userId= null, email = "nikos@gmail.com","123", firstName = "nikos", lastName = "papadopoulos"))
//        userServiceOld.save(UserOld(userId= null, email = "elina@gmail.com","123", firstName = "elina", lastName = "oikonomou"))
//        userServiceOld.save(UserOld(userId= null, email = "aris", "123", firstName = "aris", lastName = "tsach"))

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

        val libr1 = libraryService.addLibrary(Library(null,"Anagnostirio Apth",null,
        ),cityService.cityRepository?.findById(1)!!.orElse(null))
        libraryService.addLibrary(Library(null,"Dimosia Vivliothiki",null,
        ),cityService.cityRepository?.findById(1)!!.orElse(null))
        libraryService.addLibrary(Library(null,"Vivliothiki Athinas",null,
        ),cityService.cityRepository?.findById(2)!!.orElse(null))
        libraryService.addLibrary(Library(null,"Idiotiki Vivliothiki Athinas",null,
        ),cityService.cityRepository?.findById(2)!!.orElse(null))

        hasBookService.addBook(1,1,10)
        hasBookService.addBook(1,2,5)
        hasBookService.addBook(2,3,10)
        hasBookService.addBook(2,4,10)
        hasBookService.addBook(3,5,10)
        hasBookService.addBook(3,6,10)
        hasBookService.addBook(4,7,10)
        hasBookService.addBook(4,1,10)

        librarianServiceOld.save(LibrarianOld(null,"lib1","123","lib","1",libraryService.findById(1)))
        librarianServiceOld.save(LibrarianOld(null,"lib2","123","lib","2",libraryService.findById(2)))
        librarianServiceOld.save(LibrarianOld(null,"lib3","123","lib","3",libraryService.findById(3)))
        librarianServiceOld.save(LibrarianOld(null,"lib4","123","lib","4",libraryService.findById(4)))

        librarianService.save( Librarian(null,"firstName lib","lastName lib",libr1,acc4))

    }
}