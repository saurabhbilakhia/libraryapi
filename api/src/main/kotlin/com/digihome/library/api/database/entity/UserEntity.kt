package com.digihome.library.api.database.entity

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * Created by saurabhbilakhia on 2021-03-14
 */

@Entity
@Table(name = "User")
class UserEntity (
    @Id
    var id: String = "",

    var membershipId: String? = "",

    var firstName: String = "",

    var lastName: String = "",

    var phoneNumber: String = "",

    var emailId: String? = "",

    var createdBy: String = "",

    var createdDateTime: LocalDateTime = LocalDateTime.now(),

    var lastUpdatedDateTime: LocalDateTime = LocalDateTime.now()
)

interface UserRepository : JpaRepository<UserEntity, String> {
    fun findByPhoneNumber(phoneNumber: String) : UserEntity?
}