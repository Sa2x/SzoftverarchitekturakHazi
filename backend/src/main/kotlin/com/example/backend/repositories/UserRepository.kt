package com.example.backend.repositories

import com.example.backend.entities.User
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<User, Int> {
    fun existsUserByEmail(email:String):Boolean
    fun findByEmail(email:String):User
}