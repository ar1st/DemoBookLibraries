package com.aris.booklibraries.demoBookLibraries.models

data class BorrowDetails(
    var bookTitle: String?,
    var libraryName: String?,
    var cityName: String?,
    var borrowDate: String?,
    var returnDate: String?,
    var hasBookId: String
)