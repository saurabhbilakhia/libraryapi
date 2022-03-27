package com.digihome.library.api.models

/**
 * Created by saurabhbilakhia on 2021-03-14
 */

data class ServiceResponseModel (
    val success: Boolean,
    val message: String,
    val data: Any? = null
)