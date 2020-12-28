package com.aris.booklibraries.demoBookLibraries.executors

import com.aris.booklibraries.demoBookLibraries.models.City
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import com.aris.booklibraries.demoBookLibraries.services.CityService
import com.aris.booklibraries.demoBookLibraries.services.LibraryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletResponse

@Component
class CityExecutor {
    @Autowired
    lateinit var cityService: CityService
    @Autowired
    lateinit var libraryService: LibraryService

    fun getAllCities(): ApiResponse<List<City>, String> {
        val allCities = cityService.findAll()
        return if ( allCities!= null) {
            ApiResponse(data = allCities, message = "OK")
        } else {
            ApiResponse(data = null, message = "Error: Sth went wrong")
        }
    }

    fun getCityById(cityId: Long,response: HttpServletResponse): ApiResponse<City,String> {
        val cityToReturn = cityService.findById(cityId)

        return if ( cityToReturn != null ) {
            ApiResponse(data=cityToReturn, message = "OK")
        } else {
            response.status = HttpStatus.BAD_REQUEST.value()
            ApiResponse(data = null, message = "Error: No city with such id.")
        }
    }

    fun createCity( data: City,
                    response: HttpServletResponse ): ApiResponse<City,String> {
        if (data.name == null) {
            response.status = HttpStatus.BAD_REQUEST.value()
            return ApiResponse(data=null,message="Error: Insert a name.")
        }

        response.status = HttpStatus.ACCEPTED.value()
        return ApiResponse( data = cityService.save(data), message = "OK" )
    }

    fun deleteCity(cityId: Long, response: HttpServletResponse): ApiResponse<String, String> {
        val cityToDelete = cityService.findById(cityId)

        if ( cityToDelete != null ) {
            libraryService.deleteByCity(cityToDelete.cityId!!)
            cityService.deleteById(cityToDelete.cityId!!)
        }

        return ApiResponse(data = null, message = "OK")
    }
}