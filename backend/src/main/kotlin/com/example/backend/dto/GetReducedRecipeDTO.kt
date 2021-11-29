package com.example.backend.dto

import com.example.backend.entities.Diet

data class GetReducedRecipeDTO(
    val id: Int,
    val name:String,
    val imageURL:String,
    val user:GetUserForGetRecipeDTO?,
    val likes: List<GetUserForGetRecipeDTO>?,
    val diets: Set<Diet>?,
)