package com.example.backend.dto

data class GetRecipeDTO (
    val id: Int,
    val name:String,
    val user:GetUserForGetRecipeDTO,
)