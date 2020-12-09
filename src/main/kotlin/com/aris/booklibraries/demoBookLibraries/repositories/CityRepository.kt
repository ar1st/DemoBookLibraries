package com.aris.booklibraries.demoBookLibraries.repositories

import com.aris.booklibraries.demoBookLibraries.models.City
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CityRepository : JpaRepository<City,Long>{
}