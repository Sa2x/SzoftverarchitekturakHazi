package com.example.backend.dto

data class GetReducedRecipeDTO(
    val id: Int,
    val name:String,
    val imageURL:String,
    val user:GetUserForGetRecipeDTO,
    val likes: List<GetUserForGetRecipeDTO>?
)