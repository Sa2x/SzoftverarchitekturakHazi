package com.example.backend.controllers

import com.example.backend.auth.Auth
import com.example.backend.dto.CreateRecipeDTO
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

    @GetMapping
    fun getAllRecipe(): ResponseEntity<Any> = ResponseEntity.ok(recipeRepository.findAll())

    @PostMapping
    fun uploadRecipe(@Auth user: User, @RequestBody recipe: CreateRecipeDTO): ResponseEntity<Any> {
        if (userRepository.existsById(user.id)) {
            val foundUser: User = userRepository.findById(user.id).get()
            val newUploadedRecipes: MutableList<Recipe>? = foundUser.uploadedRecipes as MutableList<Recipe>?
            newUploadedRecipes?.add(Recipe(name = recipe.name, user = foundUser))
            userRepository.save(foundUser.copy(uploadedRecipes = newUploadedRecipes))
            return ResponseEntity.ok().build()
        }
        return ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteRecipe(@Auth user: User, @PathVariable id: Int): ResponseEntity<Any> {
        if (recipeRepository.existsById(id)) {
            val recipe: Recipe = recipeRepository.findById(id).orElse(null)
            if (user.id == recipe.user.id) {
                recipeRepository.delete(recipe)
                return ResponseEntity.ok().build()
            }
            return ResponseEntity("You can't delete other user's recipes", HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity("The recipe doesn't exist with the given id", HttpStatus.NOT_FOUND)
    }

    @PutMapping("/{id}")
    fun updateRecipe(
        @Auth user: User,
        @PathVariable id: Int,
        @RequestBody updatedRecipe: CreateRecipeDTO
    ): ResponseEntity<Any> {
        if (recipeRepository.existsById(id)) {
            val recipe: Recipe = recipeRepository.findById(id).orElse(null)
            if (user.id == recipe.user.id) {
                recipeRepository.save(recipe.copy(name = updatedRecipe.name))
                return ResponseEntity.ok().build()
            }
            return ResponseEntity("You can't modify other user's recipes", HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity("The recipe doesn't exist with the given id", HttpStatus.NOT_FOUND)
    }

    @PostMapping("/{id}/like")
    fun likeRecipe(@Auth user: User, @PathVariable id: Int): ResponseEntity<Any> {
        val foundUser: User? = userRepository.findById(user.id).orElse(null)
        val likedRecipes = foundUser!!.likedRecipes as MutableSet<Recipe>
        if (recipeRepository.existsById(id)) {
            likedRecipes.add(recipeRepository.findById(id).get())
            return ResponseEntity.ok().build()
        } else {
            return ResponseEntity("Recipe with the given id doesnt exist", HttpStatus.NOT_FOUND)
        }

    }

    @GetMapping("/{id}/likes")
    fun getLikesOfRecipe(@Auth user: User, @PathVariable id: Int): ResponseEntity<Any> {
        if (recipeRepository.existsById(id)) {
            return ResponseEntity.ok(recipeRepository.findById(id).get().likes?.map { liker ->
                User(liker.id, liker.userName, liker.email)
            })
        }
        return ResponseEntity("Not found recipe with given id",HttpStatus.NOT_FOUND)
    }

}
