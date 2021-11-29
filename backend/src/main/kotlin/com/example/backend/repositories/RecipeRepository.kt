package com.example.backend.repositories

import com.example.backend.entities.Recipe
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.*

interface RecipeRepository: CrudRepository<Recipe, Int> {

//     @Query("SELECT r from Recipe r left JOIN FETCH r.diets")
//     override fun findAll(): MutableSet<Recipe>
//
//     @Query("SELECT r from Recipe r left JOIN FETCH r.ingredients left JOIN FETCH r.diets WHERE r.id = (:id)")
//     override fun findById(id: Int): Optional<Recipe>
}