package com.aris.booklibraries.demoBookLibraries.services

import com.aris.booklibraries.demoBookLibraries.models.Address
import com.aris.booklibraries.demoBookLibraries.models.Author
import com.aris.booklibraries.demoBookLibraries.models.Book
import com.aris.booklibraries.demoBookLibraries.models.User
import com.aris.booklibraries.demoBookLibraries.repositories.AddressRepository
import com.aris.booklibraries.demoBookLibraries.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
class UserService {
    @Autowired
    var userRepository: UserRepository? = null
    @Autowired
    var addressRepository: AddressRepository? = null

    fun findById(id: Long): User? {
        return userRepository?.findById(id)?.orElse(null)
    }

    fun findByFirstName(firstName: String): User? {
        return userRepository?.findByFirstName(firstName)
    }

    @Transactional
    fun addUser(entity: User, address: Address): User? {
        val matchedAddress = if ( address.addressId == null) {
            addressRepository?.save(address)
        } else {
            addressRepository?.findById( address.addressId!! )?.orElse(null)
        } ?: return null

        entity.address = matchedAddress
        return userRepository?.save(entity)
    }
}