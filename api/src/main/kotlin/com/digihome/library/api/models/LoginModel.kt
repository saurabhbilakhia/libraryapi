package com.digihome.library.api.models

/**
 * Created by saurabhbilakhia on 2022-04-10
 */

data class LoginModel (
    val username: String = "",
    val password: String = ""
)

data class LoginResponseModel (
    val id: String = "",
    val firstName: String? = null,
    val lastName: String? = null
)