package com.aris.booklibraries.demoBookLibraries.models

data class BorrowDetails(
    var bookTitle: String?,
    var libraryName: String?,
    var cityName: String?,
    var borrowDate: String?,
    var hasBookId: String
)