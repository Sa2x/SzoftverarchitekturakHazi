package com.example.backend.controllers

import com.example.backend.dto.GetRecipeDTO
import com.example.backend.dto.GetUserForGetRecipeDTO
import com.example.backend.dto.LoginUserDto
import com.example.backend.dto.RegisterUserDTO
import com.example.backend.entities.Recipe
import com.example.backend.entities.User
import com.example.backend.repositories.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/user")
class UserController(private val userRepository: UserRepository) {


    @PostMapping("/register")
    fun register(@RequestBody user: RegisterUserDTO):ResponseEntity<Any>{
        if(!userRepository.existsUserByEmail(user.email)){
            val savedUser: User = userRepository.save(User(0,user.userName,user.email,user.password))
            return ResponseEntity.ok(savedUser.id)
        }
        return ResponseEntity("User with the given email already exists", HttpStatus.BAD_REQUEST)
    }

    @PostMapping("/login")
    fun login(@RequestBody user:LoginUserDto):ResponseEntity<Any>{
        return if(userRepository.existsUserByEmail(user.email)){
            val foundUser = userRepository.findByEmail(user.email)
            if(foundUser.password == user.password){
                ResponseEntity.ok(foundUser)
            } else{
                ResponseEntity("Wrong password", HttpStatus.BAD_REQUEST);
            }
        } else{
            ResponseEntity("User doesnt exist with the given email",HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/{id}/uploaded")
    fun getUploadedRecipes(@PathVariable id:Int):ResponseEntity<Any>{
        if(userRepository.existsById(id)){
            val user: User = userRepository.findById(id).orElse(null);
            return ResponseEntity.ok(user.uploadedRecipes?.map { recipe-> GetRecipeDTO(recipe.id,recipe.name,
                GetUserForGetRecipeDTO(recipe.user.id,recipe.user.userName,recipe.user.email)) });
        }
        return ResponseEntity("No user exists with the given id",HttpStatus.NOT_FOUND);
    }
}