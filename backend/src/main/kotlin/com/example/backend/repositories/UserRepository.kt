package com.example.backend.repositories

import com.example.backend.entities.Recipe
import com.example.backend.entities.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserRepository: CrudRepository<User, Int> {
    fun existsUserByEmail(email:String):Boolean
    fun findByEmail(email:String):User

//    @Query("SELECT u from User u left JOIN FETCH u.likedRecipes")
//    override fun findAll(): MutableSet<User>
//
//    @Query("SELECT u from User u left JOIN FETCH u.likedRecipes WHERE u.id = (:id)")
//    override fun findById(id: Int): Optional<User>
}