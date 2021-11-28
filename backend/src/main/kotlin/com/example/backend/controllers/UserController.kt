package com.example.backend.controllers

import com.example.backend.auth.Auth
import com.example.backend.dto.*
import com.example.backend.entities.User
import com.example.backend.repositories.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/api/user")
class UserController(private val userRepository: UserRepository) {


    @GetMapping
    fun getAllUsers(): ResponseEntity<Any> = ResponseEntity.ok(
        userRepository.findAll().map { user -> GetUserForGetRecipeDTO(user.id, user.userName, user.email) })

    @PostMapping("/register")
    fun register(@RequestBody user: RegisterUserDTO): ResponseEntity<Any> {
        if (!userRepository.existsUserByEmail(user.email)) {
            val savedUser: User = userRepository.save(User(0, user.userName, user.email, user.password))
            return ResponseEntity.ok(savedUser.id)
        }
        return ResponseEntity("User with the given email already exists", HttpStatus.BAD_REQUEST)
    }

    @PostMapping("/login")
    fun login(@RequestBody user: LoginUserDto, response: HttpServletResponse): ResponseEntity<Any> {
        return if (userRepository.existsUserByEmail(user.email)) {
            val foundUser = userRepository.findByEmail(user.email)
            if (foundUser.password == user.password) {
                val claims: Map<String, Any> = hashMapOf(
                    "user_id" to foundUser.id,
                    "user_name" to foundUser.userName,
                    "user_email" to foundUser.email
                )
                val jwt = Jwts.builder()
                    .addClaims(claims)
                    .setIssuer("backend")
                    .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 1000))
                    .signWith(SignatureAlgorithm.HS512, "secret")
                    .compact()
                ResponseEntity.ok(TokenDTO(jwt))
            } else {
                ResponseEntity("Wrong password", HttpStatus.BAD_REQUEST)
            }
        } else {
            ResponseEntity("User doesnt exist with the given email", HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/me")
    fun user(@Auth user: User): ResponseEntity<Any> {
        return ResponseEntity.ok(user)
    }

    @Transactional
    @GetMapping("/{id}/uploaded")
    fun getUploadedRecipes(@PathVariable id: Int): ResponseEntity<Any> {
        if (userRepository.existsById(id)) {
            val user: User = userRepository.findById(id).orElse(null)
            return ResponseEntity.ok(user.uploadedRecipes?.map { recipe ->
                GetRecipeDTO(
                    recipe.id, recipe.name, "http://localhost:8080/api/recipe/" + recipe.id + "/picture",
                    GetUserForGetRecipeDTO(recipe.user.id, recipe.user.userName, recipe.user.email),
                    recipe.description,
                    recipe.ingredients,
                    recipe.diets
                )
            })
        }
        return ResponseEntity("No user exists with the given id", HttpStatus.NOT_FOUND)
    }

    @Transactional
    @GetMapping("/{id}/liked")
    fun getLikedRecipes(@PathVariable id: Int): ResponseEntity<Any> {
        if (userRepository.existsById(id)) {
            val user: User = userRepository.findById(id).orElse(null)
            return ResponseEntity.ok(user.likedRecipes?.map { recipe ->
                GetRecipeDTO(
                    recipe.id, recipe.name, "http://localhost:8080/api/recipe/" + recipe.id + "/picture",
                    GetUserForGetRecipeDTO(recipe.user.id, recipe.user.userName, recipe.user.email),
                    recipe.description,
                    recipe.ingredients,
                    recipe.diets
                )
            })
        }
        return ResponseEntity("No user exists with the given id", HttpStatus.NOT_FOUND)
    }

    @Transactional
    @PostMapping("/{id}/follow")
    fun followUser(@Auth user: User, @PathVariable id: Int): ResponseEntity<Any> {
        if (userRepository.existsById(id)) {
            val toFollowUser: User = userRepository.findById(id).orElse(null)
            val wantToFollowUser: User = userRepository.findById(user.id).orElse(null)
            (wantToFollowUser.followedUsers as MutableSet<User>).add(toFollowUser)
            return ResponseEntity.ok().build()
        }
        return ResponseEntity("User doesnt exist with given id", HttpStatus.NOT_FOUND)
    }

    @Transactional
    @PostMapping("/{id}/unfollow")
    fun unfollowUser(@Auth user: User, @PathVariable id: Int): ResponseEntity<Any> {
        if (userRepository.existsById(id)) {
            val toFollowUser: User = userRepository.findById(id).orElse(null)
            val wantToFollowUser: User = userRepository.findById(user.id).orElse(null)
            (wantToFollowUser.followedUsers as MutableSet<User>).remove(toFollowUser)
            return ResponseEntity.ok().build()
        }
        return ResponseEntity("User doesnt exist with given id", HttpStatus.NOT_FOUND)
    }

    @Transactional
    @GetMapping("/{id}/followed")
    fun getFollowedUsers(@PathVariable id: Int): ResponseEntity<Any> {
        if (userRepository.existsById(id)) {
            val user: User = userRepository.findById(id).orElse(null)
            return ResponseEntity.ok(user.followedUsers?.map { followed ->
                GetUserForGetRecipeDTO(followed.id, followed.userName, followed.email)
            })
        }
        return ResponseEntity("No user exists with the given id", HttpStatus.NOT_FOUND)
    }

    @Transactional
    @GetMapping("/{id}/followers")
    fun getFollowers(@PathVariable id: Int): ResponseEntity<Any> {
        if (userRepository.existsById(id)) {
            val user: User = userRepository.findById(id).orElse(null)
            return ResponseEntity.ok(user.followers?.map { follower ->
                GetUserForGetRecipeDTO(follower.id, follower.userName, follower.email)
            })
        }
        return ResponseEntity("No user exists with the given id", HttpStatus.NOT_FOUND)
    }
}