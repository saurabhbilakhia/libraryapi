package com.digihome.library.api.models

/**
 * Created by saurabhbilakhia on 2021-03-14
 */

data class ResponseModel (
    val success: Boolean = true,
    val message: String? = null,
    val code: Int = 200,
    val data: Any? = null
)