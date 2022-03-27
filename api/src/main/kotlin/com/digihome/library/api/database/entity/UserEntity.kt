package com.digihome.library.api.database.entity

import org.springframework.data.jpa.repository.JpaRepository
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

    var emailId: String? = ""
)

interface UserRepository : JpaRepository<UserEntity, String> {
    fun findByPhoneNumber(phoneNumber: String) : UserEntity?
}