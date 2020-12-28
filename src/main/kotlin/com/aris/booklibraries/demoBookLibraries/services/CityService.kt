package com.aris.booklibraries.demoBookLibraries.services

import com.aris.booklibraries.demoBookLibraries.models.City
import com.aris.booklibraries.demoBookLibraries.repositories.CityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CityService {
    @Autowired
    var cityRepository: CityRepository? = null

    @Transactional
    fun findAll() : List<City> {
        return cityRepository?.findAll() ?: emptyList()
    }

    @Transactional
    fun findById(cityId: Long) : City? {
        return cityRepository?.findById(cityId)?.orElse( null)
    }

    @Transactional
    fun save(entity: City) : City? {
        return cityRepository?.save(entity)
    }

    @Transactional
    fun deleteById( libraryId: Long) {
        cityRepository?.deleteById(libraryId)
    }
}