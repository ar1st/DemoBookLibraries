package com.aris.booklibraries.demoBookLibraries.models.response

//good practice to use ApiResponse
data class ApiResponse<D, E>(var data: D?, var message: E? )
{

}
