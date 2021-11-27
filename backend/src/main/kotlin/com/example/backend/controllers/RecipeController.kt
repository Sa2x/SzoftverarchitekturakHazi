package com.example.backend.controllers

import com.example.backend.dto.CreateRecipeDTO
import com.example.backend.dto.DeleteRecipeDTO
import com.example.backend.entities.Recipe
import com.example.backend.entities.User
import com.example.backend.repositories.RecipeRepository
import com.example.backend.repositories.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/recipe")
class RecipeController(private val recipeRepository: RecipeRepository, private val userRepository: UserRepository) {

    @PostMapping
    fun uploadRecipe(@RequestBody recipe: CreateRecipeDTO): ResponseEntity<Any> {
        val user: Optional<User> = userRepository.findById(recipe.creatorId);
        if (user.isPresent) {
            val foundUser: User = user.get()
            val newUploadedRecipes: MutableList<Recipe>? = foundUser.uploadedRecipes as MutableList<Recipe>?
            newUploadedRecipes?.add(Recipe(name = recipe.name, user = foundUser))
            userRepository.save(foundUser.copy(uploadedRecipes = newUploadedRecipes))
            return ResponseEntity.ok().build()
        }
        return ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteRecipe(@PathVariable id: Int, @RequestBody body: DeleteRecipeDTO): ResponseEntity<Any> {
        if (recipeRepository.existsById(id)) {
            val recipe: Recipe = recipeRepository.findById(id).orElse(null)
            if (body.userId == recipe.user.id) {
                recipeRepository.delete(recipe)
                return ResponseEntity.ok().build()
            }
            return ResponseEntity("The user id doesn't match the recipe's creator ID", HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity("The recipe doesn't exist with the given id", HttpStatus.NOT_FOUND)
    }

    @PutMapping("/{id}")
    fun updateRecipe(@PathVariable id:Int, @RequestBody recipe:CreateRecipeDTO):ResponseEntity<Any> {
        return ResponseEntity.ok();
    }
}