package com.digihome.library.api.models

/**
 * Created by saurabhbilakhia on 2021-03-14
 */

data class AddBookModel (
    var bookName: String = "",
    var author: String = "",
    var publication: String = "",
    var language: String = "",
    var location: String = "",
    var numberOfCopies: Int = 0
)

data class BookIssueModel (
    var userId: String = "",
    var bookId: String = ""
)