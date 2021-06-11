package com.digihome.library.api.database.dbservice

import com.digihome.library.api.database.entity.BookIssueEntity
import com.digihome.library.api.database.entity.BookIssueRepository
import com.digihome.library.api.database.entity.BooksEntity
import com.digihome.library.api.database.entity.BooksRepository
import com.digihome.library.api.models.AddBookModel
import com.digihome.library.api.models.BookIssueModel
import com.digihome.library.api.models.ServiceResponseModel
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

/**
 * Created by saurabhbilakhia on 2021-03-14
 */

@Service
class BooksDbService(val booksRepository: BooksRepository,
                     val bookIssueRepository: BookIssueRepository,
                     val objectMapper: ObjectMapper) {

    val logger = LoggerFactory.getLogger(this::class.java)

    fun saveABook(addBookModel: AddBookModel) : ServiceResponseModel {
        val booksEntity = BooksEntity(UUID.randomUUID().toString(),
                                        addBookModel.bookName,
                                        addBookModel.author,
                                        addBookModel.publication,
                                        addBookModel.language,
                                        addBookModel.location,
                                        addBookModel.numberOfCopies)


        booksRepository.save(booksEntity)

        return ServiceResponseModel(true, "Book added")
    }


    fun issueABook(bookIssueModel: BookIssueModel) : ServiceResponseModel {
        val bookIssueEntity = BookIssueEntity(UUID.randomUUID().toString(),
                                                bookIssueModel.userId,
                                                bookIssueModel.bookId)

        bookIssueRepository.save(bookIssueEntity)

        return ServiceResponseModel(true, "Book issued")
    }

    fun returnABook(bookIssueModel: BookIssueModel) : ServiceResponseModel {
        val bookIssueEntity = bookIssueRepository.findByUserIdAndBookIdAndReturnDateIsNull(bookIssueModel.userId, bookIssueModel.bookId)

        if(bookIssueEntity ==  null) {
            throw Exception("No book issue record found")
        } else {
            bookIssueEntity.returnDate = LocalDateTime.now()
            bookIssueRepository.save(bookIssueEntity)
        }

        return ServiceResponseModel(true, "Book returned")
    }
}