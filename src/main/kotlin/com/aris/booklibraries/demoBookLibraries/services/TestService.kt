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
    var addressService = AddressService()

    @PostConstruct
    fun postConstruct() {
        //init()
        //val book5 = bookService.addBook(Book(null,"olympus",null),authorService.findById(2)!!)
      //  val library = libraryService.findById(1) ?: Library(null,"dkwa",null, emptyList<Book>() as MutableList<Book>)
            //libraryService.addBook( library, book5!!)
    }

    fun init() {
        addressService.addressRepository?.save(Address(null,"Egnatia","3",555,"Greece"))
        addressService.addressRepository?.save(Address(null,"Agiou Dimitriou","55",555,"Greece"))
        addressService.addressRepository?.save(Address(null,"Kassandrou","10",555,"Greece"))


        userService.addUser(User(id= null, email = "giannis@gmail.com", firstName = "giannis", lastName = "kostopoulos",null),addressService.findById(1)!! )
        userService.addUser(User(id= null, email = "nikos@gmail.com", firstName = "nikos", lastName = "papadopoulos",null), addressService.findById(2)!! )
        userService.addUser(User(id= null, email = "elina@gmail.com", firstName = "elina", lastName = "oikonomou",null), addressService.findById(3)!! )



        authorService.save(Author(authorId= null, email = "petros@gmail.com", firstName = "petros", lastName = "petrou") )
        authorService.save(Author(authorId= null, email = "nikoleta@gmail.com", firstName = "nikoleta", lastName = "nikoletou") )
        authorService.save(Author(authorId= null, email = "bunny@gmail.com", firstName = "bunny", lastName = "funny") )

        val book1 = bookService.addBook(Book(null,"avatar",null),authorService.findById(1)!!)
        val book2 = bookService.addBook(Book(null,"star wars",null),authorService.findById(1)!!)
        val book3 = bookService.addBook(Book(null,"star wars 2",null),authorService.findById(1)!!)
        val book4 = bookService.addBook(Book(null,"harry potter",null),authorService.findById(2)!!)
        val book5 = bookService.addBook(Book(null,"harry potter 2",null),authorService.findById(2)!!)
        val book6 = bookService.addBook(Book(null,"harry potter 3",null),authorService.findById(2)!!)
        val book7 = bookService.addBook(Book(null,"harry potter 4",null),authorService.findById(2)!!)

        cityService.cityRepository?.save(City(null,"Thessaloniki"))
        cityService.cityRepository?.save(City(null,"Athens"))
        cityService.cityRepository?.save(City(null,"Larissa"))

        libraryService.addLibrary(Library(null,"Anagnostirio Apth",null,
            (listOf( book1,book2) as List<Book>).toMutableList()
        ),cityService.cityRepository?.findById(1)!!.orElse(null))
        libraryService.addLibrary(Library(null,"Dimosia Vivliothiki",null,
            (listOf(book3,book4) as List<Book>).toMutableList()
        ),cityService.cityRepository?.findById(1)!!.orElse(null))
        libraryService.addLibrary(Library(null,"Vivliothiki Athinas",null,
            (listOf(book5,book6) as List<Book>).toMutableList()
        ),cityService.cityRepository?.findById(2)!!.orElse(null))
        libraryService.addLibrary(Library(null,"Idiotiki Vivliothiki Athinas",null,
            (listOf(book7) as List<Book>).toMutableList()
        ),cityService.cityRepository?.findById(2)!!.orElse(null))
        libraryService.addLibrary(Library(null,"Vivliothiki Larisas",null,
            (listOf(book1) as List<Book>).toMutableList()
        ),cityService.cityRepository?.findById(3)!!.orElse(null))
        //initLibrary()

        }
}