package com.example.backend.dto

import com.example.backend.entities.Diet
import javax.persistence.Lob

data class GetRecipeDTO (
    val id: Int,
    val name:String,
    val imageURL:String,
    val user:GetUserForGetRecipeDTO,
    val description: String,
    val ingredients: Set<String>,
    val diets: Set<Diet>,
)