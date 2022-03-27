package com.digihome.library.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

/**
 * Created by saurabhbilakhia on 2021-03-14
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(val objectMapper: ObjectMapper
) : WebSecurityConfigurerAdapter() {

    val logger = LoggerFactory.getLogger(this::class.java)

    @Throws(java.lang.Exception::class)
    override fun configure(http: HttpSecurity) {

        http
            .cors().and().csrf().disable().authorizeRequests()
            .antMatchers(HttpMethod.POST, "/books/**").permitAll() // TODO: remove this line
            .antMatchers(HttpMethod.GET, "/books/**").permitAll() // TODO: remove this line
            .antMatchers(HttpMethod.POST, "/user/**").permitAll() // TODO: remove this line
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }
}