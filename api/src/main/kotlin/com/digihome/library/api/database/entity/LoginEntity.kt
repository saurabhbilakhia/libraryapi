package com.digihome.library.api.database.entity

import org.springframework.data.jpa.repository.JpaRepository
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * Created by saurabhbilakhia on 2022-04-10
 */

@Entity
@Table(name = "Login")
class LoginEntity (
    @Id
    val id: String = "",

    var username: String = "",

    var password: String = "",

    var firstName: String? = "",

    var lastName: String? = ""
)

interface LoginRepository : JpaRepository<LoginEntity, String> {
    fun findByUsername(username: String) : LoginEntity?
}
