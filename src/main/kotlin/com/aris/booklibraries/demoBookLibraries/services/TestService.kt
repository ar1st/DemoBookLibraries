package com.aris.booklibraries.demoBookLibraries.services

import com.aris.booklibraries.demoBookLibraries.executors.AccountExecutor
import com.aris.booklibraries.demoBookLibraries.models.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDate
import javax.annotation.PostConstruct

@Service
class TestService {
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
    @Autowired
    lateinit var borrowsService: BorrowsService

    @PostConstruct
    fun postConstruct() {
        init()
    }

    fun init() {
        val encoder = BCryptPasswordEncoder(10)

        val acc1 = accountService.save( Account(null,"aris",encoder.encode("pass"),1,"USER"))
        val acc2 = accountService.save( Account(null,"bill",encoder.encode("pass"),1,"USER"))
        val acc3 = accountService.save( Account(null,"nick",encoder.encode("pass"),1,"USER"))
        val acc4 = accountService.save( Account(null,"lib",encoder.encode("pass"),1,"LIBRARIAN"))

        userService.save(User(null,"Aris", "Tsach", acc1))
        userService.save(User(null,"Bill", "Pap", acc2))
        userService.save(User(null,"Nick", "Oik", acc3))

//        userServiceOld.save(UserOld(userId= null, email = "giannis@gmail.com","123", firstName = "giannis", lastName = "kostopoulos"))
//        userServiceOld.save(UserOld(userId= null, email = "nikos@gmail.com","123", firstName = "nikos", lastName = "papadopoulos"))
//        userServiceOld.save(UserOld(userId= null, email = "elina@gmail.com","123", firstName = "elina", lastName = "oikonomou"))
//        userServiceOld.save(UserOld(userId= null, email = "aris", "123", firstName = "aris", lastName = "tsach"))

        authorService.save(Author(authorId= null, email = "petros@gmail.com", firstName = "Petros", lastName = "Petrou") )
        authorService.save(Author(authorId= null, email = "nikoleta@gmail.com", firstName = "Nikoleta", lastName = "Nikoletou") )
        authorService.save(Author(authorId= null, email = "bunny@gmail.com", firstName = "Marios", lastName = "Papadopoulos") )

        val summary = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam gravida tincidunt sagittis. Interdum et malesuada fames ac ante ipsum primis in faucibus." +
                " Phasellus quam magna, venenatis vitae nisi nec, suscipit blandit ante. Nam vel neque euismod, ullamcorper urna vitae, varius lorem. Ut gravida urna orci, nec vestibulum magna faucibus a. Duis sed elementum tortor, vel consectetur nibh. Pellentesque vel pretium enim, eget iaculis neque. In feugiat accumsan varius. Proin volutpat nisi id massa finibus, sed lacinia dolor ullamcorper. Etiam est odio, sollicitudin nec turpis ut, bibendum ultrices felis. Suspendisse blandit leo sed erat sodales ullamcorper. Donec massa mauris, egestas eget enim non, vehicula iaculis mauris. "

        bookService.addBook(Book(null,"Avatar",null,summary,"111-1-11-111111-1",2018,222),authorService.findById(1)!!)
        bookService.addBook(Book(null,"Star Wars",null,summary,"111-1-22-111111-2",2019,222),authorService.findById(1)!!)
        bookService.addBook(Book(null,"Star Wars 2",null,summary,"111-1-33-111111-3",2020,222),authorService.findById(1)!!)
        bookService.addBook(Book(null,"Harry Potter",null,summary,"111-1-44-111111-4",2005,222),authorService.findById(2)!!)
        bookService.addBook(Book(null,"Harry Potter 2",null,summary,"111-1-55-111111-5",2010,222),authorService.findById(2)!!)
        bookService.addBook(Book(null,"Harry Potter 3",null,summary,"111-1-66-111111-6",2011,222),authorService.findById(2)!!)
        bookService.addBook(Book(null,"Harry Potter 4",null,summary,"111-1-77-111111-7",2008,222),authorService.findById(2)!!)

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

        hasBookService.addBook(1,1,0)
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


        val borrows = Borrows (null,acc1!!, HasBook(1,null,null,null), LocalDate.now(),null)
        borrowsService.save(borrows)
    }
}