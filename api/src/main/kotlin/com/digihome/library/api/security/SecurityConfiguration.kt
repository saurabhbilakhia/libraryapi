package com.digihome.library.api.security

import com.digihome.library.api.configuration.JwtConfig
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

/**
 * Created by saurabhbilakhia on 2021-03-14
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(val libraryUserDetailService: LibraryUserDetailService,
                     val passwordEncoder: BCryptPasswordEncoder,
                     val jwtConfig: JwtConfig,
                     val objectMapper: ObjectMapper) : WebSecurityConfigurerAdapter() {

    val logger = LoggerFactory.getLogger(this::class.java)

    @Throws(java.lang.Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .requiresChannel()
            .anyRequest()
            .requiresSecure()

        http
            .cors().and().csrf().disable().authorizeRequests()
            .antMatchers(HttpMethod.POST, jwtConfig.url).permitAll()
            .antMatchers(HttpMethod.POST, "/user/add").permitAll()
            .antMatchers(HttpMethod.POST, "/user/resetPassword").permitAll()
            .antMatchers(HttpMethod.POST, "/user/forgotPassword").permitAll()
            .antMatchers(HttpMethod.POST, "/test/**").permitAll() // TODO: remove this line
            .anyRequest().authenticated()
            .and()
            .addFilter(JWTAuthenticationFilter(authenticationManager(), jwtConfig, objectMapper))
            .addFilter(JWTAuthorizationFilter(authenticationManager(), jwtConfig, objectMapper))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Throws(java.lang.Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(libraryUserDetailService).passwordEncoder(passwordEncoder)
    }
}