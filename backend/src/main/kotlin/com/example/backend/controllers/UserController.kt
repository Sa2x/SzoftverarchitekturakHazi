package com.example.backend.controllers

import com.example.backend.entities.User
import com.example.backend.repositories.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(private val userRepository: UserRepository) {


    @PostMapping("/register")
    fun register(@RequestBody user: User):ResponseEntity<Any>{
        if(!userRepository.existsUserByEmail(user.email)){
            userRepository.save(user)
            return ResponseEntity.ok().build()
        }
        return ResponseEntity("User with the given email already exists", HttpStatus.BAD_REQUEST)
    }

    @PostMapping("/login")
    fun login(@RequestBody user:User):ResponseEntity<Any>{
        return if(userRepository.existsUserByEmail(user.email)){
            val foundUser = userRepository.findByEmail(user.email)
            if(foundUser.password.equals(user.password)){
                ResponseEntity.ok(foundUser)
            } else{
                ResponseEntity("Wrong password", HttpStatus.BAD_REQUEST);
            }
        } else{
            ResponseEntity("User doesnt exist with the given email",HttpStatus.BAD_REQUEST)
        }
    }
}