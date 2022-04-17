package com.digihome.library.api.security

import org.springframework.security.core.context.SecurityContextHolder

/**
 * Created by saurabhbilakhia on 2022-04-10
 */

fun getPrincipal() : LibraryUserPrincipal {
    return SecurityContextHolder.getContext().authentication.principal as LibraryUserPrincipal
}