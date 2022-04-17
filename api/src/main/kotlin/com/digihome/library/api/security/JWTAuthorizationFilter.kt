package com.digihome.library.api.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.digihome.library.api.configuration.JwtConfig
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by saurabhbilakhia on 2022-04-10
 */

class JWTAuthorizationFilter(authManager: AuthenticationManager,
                             val jwtConfig: JwtConfig,
                             val objectMapper: ObjectMapper
) : BasicAuthenticationFilter(authManager) {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader(jwtConfig.header)
        if (header == null || !header.startsWith(jwtConfig.prefix)) {
            chain.doFilter(request, response)
            return
        }
        val authentication = getAuthentication(request)
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader(jwtConfig.header)
        if (token != null) {
            val principalJson = JWT.require(Algorithm.HMAC512(jwtConfig.secret))
                .build()
                .verify(token.replace(jwtConfig.prefix, ""))
                .subject
            return if (principalJson != null) {
                val principal = objectMapper.readValue(principalJson, LibraryUserPrincipal::class.java)
                UsernamePasswordAuthenticationToken(principal, null, principal.authorities)
            } else null
        }
        return null
    }
}