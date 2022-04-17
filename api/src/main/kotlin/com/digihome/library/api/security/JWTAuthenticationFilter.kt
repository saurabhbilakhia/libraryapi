package com.digihome.library.api.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm.HMAC512
import com.digihome.library.api.configuration.JwtConfig
import com.digihome.library.api.models.LoginModel
import com.digihome.library.api.models.LoginResponseModel
import com.digihome.library.api.models.ResponseModel
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by saurabhbilakhia on 2022-04-10
 */

class JWTAuthenticationFilter(val authManager: AuthenticationManager,
                              val jwtConfig: JwtConfig,
                              val objectMapper: ObjectMapper
) : UsernamePasswordAuthenticationFilter() {

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(req: HttpServletRequest,
                                       res: HttpServletResponse?): Authentication? {
        return try {
            val loginModel: LoginModel = ObjectMapper().readValue(req.inputStream, LoginModel::class.java)
            authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    loginModel.username,
                    loginModel.password,
                    ArrayList()
                ) // TODO: send an array of roles here
            )
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(req: HttpServletRequest?,
                                          res: HttpServletResponse,
                                          chain: FilterChain?,
                                          auth: Authentication
    ) {
        val principal = auth.principal as LibraryUserPrincipal
        val loginResponseModel = LoginResponseModel(principal.id, principal.firstName, principal.lastName)
        val responseModel = ResponseModel(message = "Login successful", data = loginResponseModel)
        val responseModelJson = objectMapper.writeValueAsString(responseModel)
        val principalJson = objectMapper.writeValueAsString(auth.principal)
        logger.info("$principalJson")
        val token: String = JWT.create()
            .withSubject(principalJson)
            .withExpiresAt(Date(System.currentTimeMillis() + jwtConfig.expiration))
            .sign(HMAC512(jwtConfig.secret))
        res.addHeader(jwtConfig.header, jwtConfig.prefix + token)
        res.writer.write(responseModelJson)
        res.contentType = "application/json";
        res.characterEncoding = "UTF-8";
        res.writer.flush()
        res.writer.close()
    }
}