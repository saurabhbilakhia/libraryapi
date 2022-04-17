package com.digihome.library.api.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * Created by saurabhbilakhia on 2022-04-10
 */

@Configuration
@ConfigurationProperties(prefix = "jwt")
data class JwtConfig(
    var url: String = "",
    var header: String = "",
    var prefix: String = "",
    var expiration: Long = 86400,
    var secret: String = ""
)