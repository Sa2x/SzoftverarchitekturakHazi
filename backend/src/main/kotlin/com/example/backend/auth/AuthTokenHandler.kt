package com.example.backend.auth

import com.example.backend.entities.User
import com.example.backend.repositories.UserRepository
import io.jsonwebtoken.Jwts
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class AuthTokenHandler(private val userRepository: UserRepository) {


    @Transactional
    fun getUserFromToken(token: String?): User? {
        if (token == null)
            throw AuthException("JWT Token not found")
        val claims = Jwts.parser().setSigningKey("secret").parseClaimsJws(token).body
        if(!userRepository.existsById(claims["user_id"] as Int)){
            throw AuthException("Authentication failed, JWT is not valid")
        }
        // return userRepository.findById(claims["user_id"] as Int).orElse(null)
        return User(claims["user_id"] as Int, claims["user_name"] as String, claims["user_email"] as String)
    }

}