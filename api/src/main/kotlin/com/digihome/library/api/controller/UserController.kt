package com.digihome.library.api.controller

import com.digihome.library.api.database.dbservice.UserDbService
import com.digihome.library.api.models.AddUserModel
import com.digihome.library.api.models.ResponseModel
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by saurabhbilakhia on 2021-06-01
 */

@RestController
@RequestMapping("/user")
class UserController (val userDbService: UserDbService) {

    val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/add")
    fun addBook(@RequestBody addUserModel: AddUserModel) : ResponseEntity<ResponseModel> {
        logger.info("Add user request: $addUserModel")

        return try {
            val serviceResponse = userDbService.saveUser(addUserModel)
            logger.info("success : $serviceResponse")
            ResponseEntity(ResponseModel(true, serviceResponse.message, HttpStatus.OK.value()), HttpStatus.OK)
        } catch (iae: IllegalArgumentException) {
            logger.error("failure")
            ResponseEntity(ResponseModel(success = false, message = iae.message.toString(), code = HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            logger.error("failure")
            ResponseEntity(ResponseModel(success = false, message = e.message.toString(), code = HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}