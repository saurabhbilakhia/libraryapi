package com.digihome.library.api.models

/**
 * Created by saurabhbilakhia on 2021-06-01
 */

data class AddUserModel (
    var membershipId: String? = "",
    var firstName: String = "",
    var lastName: String = "",
    var phoneNumber: String = "",
    var emailId: String? = ""
)