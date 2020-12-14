package com.aris.booklibraries.demoBookLibraries.controllers

import com.aris.booklibraries.demoBookLibraries.models.Author
import com.aris.booklibraries.demoBookLibraries.models.City
import com.aris.booklibraries.demoBookLibraries.services.AuthorService
import com.aris.booklibraries.demoBookLibraries.services.CityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping(value = ["/cities"])
class CityController {
    @Autowired
    lateinit var cityService: CityService

    @GetMapping("")
    fun getAllCities(): List<City> {
        return cityService.findAll()
    }

    @GetMapping("/{ID}")
    fun getCityById (@PathVariable("ID",required = true) cityId: Long): City {
        return cityService.findById(cityId) ?: City(cityId = null, name = "Not Found" )
    }

    @PostMapping("")
    fun createCity(request: HttpServletRequest, response: HttpServletResponse,
                     @RequestBody data: City):  City {
        var createdCity =  data
        if ( createdCity.name == null) {
            response.status = HttpStatus.BAD_REQUEST.value()
            //todo: add error message ("No email found)
        }
        cityService.save(createdCity)
        response.status = HttpStatus.ACCEPTED.value()

        return createdCity
    }
}