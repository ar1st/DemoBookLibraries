package com.aris.booklibraries.demoBookLibraries.services

import com.aris.booklibraries.demoBookLibraries.models.Address
import com.aris.booklibraries.demoBookLibraries.models.Author
import com.aris.booklibraries.demoBookLibraries.repositories.AddressRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AddressService {
    @Autowired
    var addressRepository : AddressRepository? = null

    @Transactional
    fun findById(id: Long): Address? {
        return addressRepository?.findById(id)?.orElse(null)
    }

}