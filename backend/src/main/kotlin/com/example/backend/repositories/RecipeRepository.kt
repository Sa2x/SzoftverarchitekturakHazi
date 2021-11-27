package com.example.backend.repositories

import com.example.backend.entities.Recipe
import org.springframework.data.repository.CrudRepository

interface RecipeRepository: CrudRepository<Recipe, Int> {
}