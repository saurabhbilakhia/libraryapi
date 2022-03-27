package com.digihome.library.api.database.dbservice

import com.digihome.library.api.database.entity.BookIssueEntity
import com.digihome.library.api.database.entity.BookIssueRepository
import com.digihome.library.api.database.entity.BooksEntity
import com.digihome.library.api.database.entity.BooksRepository
import com.digihome.library.api.models.*
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime
import java.util.*


/**
 * Created by saurabhbilakhia on 2021-03-14
 */

@Service
class BooksDbService(val booksRepository: BooksRepository,
                     val bookIssueRepository: BookIssueRepository,
                     val restTemplate: RestTemplate,
                     val objectMapper: ObjectMapper) {

    val logger = LoggerFactory.getLogger(this::class.java)

    @Value("\${qrcode-url}")
    lateinit var qrCodeApiUrl: String

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

    fun generateQrCode(pageNumber: Int) : ByteArray? {
        val booksEntityList = booksRepository.findAllByOrderByCreatedDateTimeAsc()

        return if(booksEntityList!!.size < pageNumber) {
            null
        } else {
            val index = pageNumber-1

            val qrCodeDataModel = QrCodeDataModel(
                booksEntityList[index].id,
                booksEntityList[index].bookName,
                booksEntityList[index].author,
                booksEntityList[index].publication,
                booksEntityList[index].language
            )

            val qrCodeData = objectMapper.writeValueAsString(qrCodeDataModel)
            val result = restTemplate.getForObject(qrCodeApiUrl, ByteArray::class.java, qrCodeData)

            result
        }
    }

    fun findBook(language: String, limit: Int, offset: Int) : ServiceResponseModel {
        val booksEntityList = booksRepository.findByLanguageOrderByBookNameAsc(language)

        return if(booksEntityList!!.isEmpty()) {
            return ServiceResponseModel(true, "No books found")
        } else {
            val startIndex = (limit * (offset - 1)) + 1
            val endIndex = limit * offset

            if(startIndex <= booksEntityList.size) {
                if(endIndex <= booksEntityList.size) {
                    return ServiceResponseModel(true, "", booksEntityList.subList(startIndex, endIndex))
                } else {
                    return ServiceResponseModel(true, "", booksEntityList.subList(startIndex, booksEntityList.size))
                }
            } else {
                return ServiceResponseModel(true, "No books found")
            }
        }
    }
}