package com.example.backend.controllers

import com.example.backend.auth.Auth
import com.example.backend.dto.CreateRecipeDTO
import com.example.backend.dto.GetRecipeDTO
import com.example.backend.dto.GetReducedRecipeDTO
import com.example.backend.dto.GetUserForGetRecipeDTO
import com.example.backend.entities.Diet
import com.example.backend.entities.Recipe
import com.example.backend.entities.User
import com.example.backend.mail.EmailService
import com.example.backend.repositories.RecipeRepository
import com.example.backend.repositories.UserRepository
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/recipe")
class RecipeController(
    private val recipeRepository: RecipeRepository,
    private val userRepository: UserRepository,
    private val emailService: EmailService
) {

    @Transactional
    @GetMapping
    fun getAllRecipe(
        @RequestParam("creator") creator: String?,
        @RequestParam("ingredient") ingredient: String?,
        @RequestParam("diet") diet: Diet?,
        @RequestParam("name") name: String?,
    ): ResponseEntity<Any> = ResponseEntity.ok(recipeRepository.findAll()
        .filter { recipe -> creator.isNullOrBlank() || recipe.user.userName.contains(creator) }
        .filter { recipe -> ingredient.isNullOrBlank() || recipe.ingredients.contains(ingredient) }
        .filter { recipe -> diet == null || recipe.diets.contains(diet)}
        .filter { recipe -> name.isNullOrBlank() || recipe.name.contains(name) }
        .map { recipe ->
            GetReducedRecipeDTO(
                recipe.id,
                recipe.name,
                "http://localhost:8080/api/recipe/" + recipe.id + "/picture",
                GetUserForGetRecipeDTO(recipe.user.id, recipe.user.userName, recipe.user.email),
                likes = recipe.likes?.map{ user-> GetUserForGetRecipeDTO(user.id, user.userName, user.email)},
                diets = recipe.diets
            )
        })

    @Transactional
    @GetMapping("/{id}")
    fun getRecipeById(@PathVariable id: Int): ResponseEntity<Any> {
        if (recipeRepository.existsById(id)) {
            val recipe: Recipe = recipeRepository.findById(id).get()
            return ResponseEntity.ok(
                GetRecipeDTO(
                    recipe.id,
                    recipe.name,
                    "http://localhost:8080/api/recipe/" + recipe.id + "/picture",
                    GetUserForGetRecipeDTO(recipe.user.id, recipe.user.userName, recipe.user.email),
                    recipe.description,
                    recipe.ingredients,
                    recipe.diets
                )
            )
        }
        return ResponseEntity("Recipe with id not found", HttpStatus.NOT_FOUND)
    }

    @Transactional
    @PostMapping
    fun uploadRecipe(
        @Auth user: User,
        @ModelAttribute recipe: CreateRecipeDTO
    ): ResponseEntity<Any> {

        if (userRepository.existsById(user.id)) {
            val foundUser: User = userRepository.findById(user.id).get()
            val newUploadedRecipes: MutableList<Recipe>? = foundUser.uploadedRecipes as MutableList<Recipe>?
            newUploadedRecipes?.add(
                Recipe(
                    name = recipe.name,
                    user = foundUser,
                    recipePicture = recipe.file.bytes,
                    description = recipe.description,
                    ingredients = recipe.ingredients,
                    diets = recipe.diets
                )
            )
            userRepository.save(foundUser.copy(uploadedRecipes = newUploadedRecipes))
            emailService.sendSimpleMessage(
                "hasza98@gmail.com",
                user.userName + " uploaded new recipe",
                user.userName + "just uploaded new recipe, check it out."
            )
            return ResponseEntity.ok().build()
        }
        return ResponseEntity.notFound().build()
    }

    @Transactional
    @DeleteMapping("/{id}")
    fun deleteRecipe(@Auth user: User, @PathVariable id: Int): ResponseEntity<Any> {
        if (recipeRepository.existsById(id)) {
            val recipe: Recipe = recipeRepository.findById(id).orElse(null)
            if (user.id == recipe.user.id) {
                if(!recipe.likes.isNullOrEmpty()) recipe.likes.forEach { liker -> (liker.likedRecipes?.toMutableSet())?.remove(recipe) }
                recipeRepository.delete(recipe)
                return ResponseEntity.ok().build()
            }
            return ResponseEntity("You can't delete other user's recipes", HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity("The recipe doesn't exist with the given id", HttpStatus.NOT_FOUND)
    }

    //TODO FIX IMAGE UPDATE
    @PutMapping("/{id}")
    fun updateRecipe(
        @Auth user: User,
        @PathVariable id: Int,
        @ModelAttribute updatedRecipe: CreateRecipeDTO
    ): ResponseEntity<Any> {
        if (recipeRepository.existsById(id)) {
            val recipe: Recipe = recipeRepository.findById(id).orElse(null)
            if (user.id == recipe.user.id) {
                recipeRepository.save(
                    recipe.copy(
                        name = updatedRecipe.name,
                        description = updatedRecipe.description,
                        diets = updatedRecipe.diets,
                        ingredients = updatedRecipe.ingredients,
                        recipePicture = updatedRecipe.file.bytes,
                    )
                )
                return ResponseEntity.ok().build()
            }
            return ResponseEntity("You can't modify other user's recipes", HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity("The recipe doesn't exist with the given id", HttpStatus.NOT_FOUND)
    }

    @Transactional
    @PostMapping("/{id}/like")
    fun likeRecipe(@Auth user: User, @PathVariable id: Int): ResponseEntity<Any> {
        val foundUser: User? = userRepository.findById(user.id).orElse(null)
        val likedRecipes = foundUser!!.likedRecipes as MutableSet<Recipe>
        return if (recipeRepository.existsById(id)) {
            likedRecipes.add(recipeRepository.findById(id).get())
            ResponseEntity.ok().build()
        } else {
            ResponseEntity("Recipe with the given id doesnt exist", HttpStatus.NOT_FOUND)
        }
    }

    @Transactional
    @PostMapping("/{id}/unlike")
    fun unlikeRecipe(@Auth user: User, @PathVariable id: Int): ResponseEntity<Any> {
        val foundUser: User? = userRepository.findById(user.id).orElse(null)
        val likedRecipes = foundUser!!.likedRecipes as MutableSet<Recipe>
        return if (recipeRepository.existsById(id)) {
            likedRecipes.remove(recipeRepository.findById(id).get())
            ResponseEntity.ok().build()
        } else {
            ResponseEntity("Recipe with the given id doesnt exist", HttpStatus.NOT_FOUND)
        }
    }

    @Transactional
    @GetMapping("/{id}/likes")
    fun getLikesOfRecipe(@Auth user: User, @PathVariable id: Int): ResponseEntity<Any> {
        if (recipeRepository.existsById(id)) {
            return ResponseEntity.ok(recipeRepository.findById(id).get().likes?.map { liker ->
                User(liker.id, liker.userName, liker.email)
            })
        }
        return ResponseEntity("Not found recipe with given id", HttpStatus.NOT_FOUND)
    }

    @GetMapping("/{id}/picture")
    fun getRecipePicture(@PathVariable id: Int): ResponseEntity<Any> {
        return try {
            val image: ByteArray? = recipeRepository.findById(id).get().recipePicture

            ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"${System.currentTimeMillis()}\"")
                .body(image)

        } catch (error: NoSuchElementException) {
            ResponseEntity
                .notFound()
                .build()
        }

    }
}
