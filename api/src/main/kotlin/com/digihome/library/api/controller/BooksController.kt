package com.digihome.library.api.controller

import com.digihome.library.api.database.dbservice.BooksDbService
import com.digihome.library.api.models.AddBookModel
import com.digihome.library.api.models.BookFilterModel
import com.digihome.library.api.models.BookIssueModel
import com.digihome.library.api.models.ResponseModel
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.coyote.Response
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Created by saurabhbilakhia on 2021-03-14
 */

@RestController
@RequestMapping("/books")
class BooksController(val booksDbService: BooksDbService,
                      val objectMapper: ObjectMapper) {

    val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/add")
    fun addBook(@RequestBody addBookModel: AddBookModel) : ResponseEntity<ResponseModel> {
        logger.info("Add book request $addBookModel")

        return try {
            val serviceResponse = booksDbService.saveABook(addBookModel)
            logger.info("success : $serviceResponse")
            ResponseEntity(ResponseModel(true, serviceResponse.message, HttpStatus.OK.value()), HttpStatus.OK)
        } catch (e: Exception) {
            logger.error("failure : ${e.message.toString()}")
            ResponseEntity(ResponseModel(success = false, message = e.message.toString(), code = HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/issue")
    fun issueABook(@RequestBody bookIssueModel: BookIssueModel) : ResponseEntity<ResponseModel> {
        logger.info("Issue book request $bookIssueModel")

        return try {
            val serviceResponse = booksDbService.issueABook(bookIssueModel)
            logger.info("success : $serviceResponse")
            ResponseEntity(ResponseModel(true, serviceResponse.message, HttpStatus.OK.value()), HttpStatus.OK)
        } catch (e: Exception) {
            logger.error("failure : ${e.message.toString()}")
            ResponseEntity(ResponseModel(success = false, message = e.message.toString(), code = HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/return")
    fun returnABook(@RequestBody bookIssueModel: BookIssueModel) : ResponseEntity<ResponseModel> {
        logger.info("Return book request $bookIssueModel")

        return try {
            val serviceResponse = booksDbService.returnABook(bookIssueModel)
            logger.info("success : $serviceResponse")
            ResponseEntity(ResponseModel(true, serviceResponse.message, HttpStatus.OK.value()), HttpStatus.OK)
        } catch (e: Exception) {
            logger.error("failure : ${e.message.toString()}")
            ResponseEntity(ResponseModel(success = false, message = e.message.toString(), code = HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/qrcode")
    fun getQrCodes(@RequestParam(name = "pageNumber") pageNumber: Int = 1) : ResponseEntity<ByteArray> {
        logger.info("Get QR Code request for page number $pageNumber")

        return try{
            val serviceResponse = booksDbService.generateQrCode(pageNumber)

            logger.info("success : $serviceResponse")
            val headers = HttpHeaders().apply {
                contentType = MediaType.IMAGE_PNG
            }
            ResponseEntity(serviceResponse, headers, HttpStatus.OK)
        } catch (e: Exception) {
            logger.error("failure : ${e.message.toString()}")
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/filterBooks")
    fun filterBooks(@RequestParam language: String, @RequestParam limit: Int, @RequestParam offset: Int) : ResponseEntity<ResponseModel> {
        logger.info("Fetching $offset books for language: $language with offset: $offset")

        return try {
            val serviceResponse = booksDbService.findBook(language, limit, offset)
            logger.info("success: $serviceResponse")
            ResponseEntity(ResponseModel(true, null, HttpStatus.OK.value(), serviceResponse.data), HttpStatus.OK)
        } catch (e: Exception) {
            logger.error("failure : ${e.message.toString()}")
            ResponseEntity(ResponseModel(success = false, message = e.message.toString(), code = HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}