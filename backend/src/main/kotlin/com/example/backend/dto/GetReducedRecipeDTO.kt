package com.example.backend.dto

import com.example.backend.entities.User

data class GetReducedRecipeDTO (
    val id: Int,
    val name:String,
    val imageURL:String,
    val user:GetUserForGetRecipeDTO,
    val likes:Set<User>?
)