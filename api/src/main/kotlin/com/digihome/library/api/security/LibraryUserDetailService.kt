package com.digihome.library.api.security

import com.digihome.library.api.database.entity.LoginRepository
import org.slf4j.LoggerFactory
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * Created by saurabhbilakhia on 2022-04-10
 */

@Service
class LibraryUserDetailService(val loginRepository: LoginRepository) : UserDetailsService {

    val logger = LoggerFactory.getLogger(this::class.java)

    override fun loadUserByUsername(username: String): UserDetails {
        val user = loginRepository.findByUsername(username)

        if (user == null) {
            logger.error("Username = $username does not exist in DB")
            throw UsernameNotFoundException("Username = $username does not exist in DB")
        } else {
            return LibraryUserPrincipal(user.id, user.username, user.password, user.firstName, user.lastName, setOf(
                SimpleGrantedAuthority("ADMIN"), SimpleGrantedAuthority("USER")
            ))
        }
    }
}