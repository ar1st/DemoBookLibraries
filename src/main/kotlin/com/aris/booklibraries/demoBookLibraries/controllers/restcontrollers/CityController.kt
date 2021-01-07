package com.aris.booklibraries.demoBookLibraries.controllers.restcontrollers

import com.aris.booklibraries.demoBookLibraries.executors.CityExecutor
import com.aris.booklibraries.demoBookLibraries.models.City
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import com.aris.booklibraries.demoBookLibraries.services.CityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping(value = ["/citiesrest"])
class CityController {
    @Autowired
    lateinit var cityService: CityService
    @Autowired
    lateinit var cityExecutor: CityExecutor

    @GetMapping("")
    fun getAllCities(): ApiResponse<List<City>,String> {
        return cityExecutor.getAllCities()
    }

    @GetMapping("/{ID}")
    fun getCityById (@PathVariable("ID",required = true) cityId: Long,
                     response: HttpServletResponse): ApiResponse<City,String> {
        return cityExecutor.getCityById(cityId,response)
    }

    @PostMapping("")
    fun createCity(response: HttpServletResponse,
                     @RequestBody data: City):  ApiResponse<City,String> {
        return cityExecutor.createCity(data,response)
    }

    @DeleteMapping("/{ID}")
    fun deleteCity(@PathVariable("ID",required = true) cityId: Long
                   ,response: HttpServletResponse):  ApiResponse<String,String> {
        return cityExecutor.deleteCity(cityId,response)
    }
}