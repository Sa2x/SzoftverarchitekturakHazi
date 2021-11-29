package com.example.backend.repositories

import com.example.backend.entities.Recipe
import com.example.backend.entities.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserRepository: CrudRepository<User, Int> {
    fun existsUserByEmail(email:String):Boolean
    fun findByEmail(email:String):User

    // left JOIN FETCH u.likedRecipes left JOIN FETCH u.uploadedRecipes left JOIN FETCH u.followedUsers left join fetch u.followers
    @Query("SELECT u from User u  WHERE u.id = (:id)")
    override fun findById(id: Int): Optional<User>
}