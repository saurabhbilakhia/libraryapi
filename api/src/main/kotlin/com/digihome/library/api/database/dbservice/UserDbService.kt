package com.digihome.library.api.database.dbservice

import com.digihome.library.api.database.entity.UserEntity
import com.digihome.library.api.database.entity.UserRepository
import com.digihome.library.api.models.AddUserModel
import com.digihome.library.api.models.ServiceResponseModel
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

/**
 * Created by saurabhbilakhia on 2021-06-01
 */

@Service
class UserDbService(val userRepository: UserRepository,
                    val objectMapper: ObjectMapper) {

    val logger = LoggerFactory.getLogger(this::class.java)

    fun saveUser(addUserModel: AddUserModel) : ServiceResponseModel {
        val existingUserEntity = userRepository.findByPhoneNumber(addUserModel.phoneNumber)

        if(existingUserEntity == null) {
            val userEntity = UserEntity(UUID.randomUUID().toString(),
                                        addUserModel.membershipId,
                                        addUserModel.firstName,
                                        addUserModel.lastName,
                                        addUserModel.phoneNumber,
                                        addUserModel.emailId)

            userRepository.save(userEntity)

            return ServiceResponseModel(true, "User added")
        } else {
            logger.error("User with phoneNumber ${addUserModel.phoneNumber} already exist")
            throw IllegalArgumentException("User with phoneNumber ${addUserModel.phoneNumber} already exist")
        }
    }
}