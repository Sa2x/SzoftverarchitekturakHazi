package com.example.backend.dto

import com.example.backend.entities.Recipe
import com.example.backend.entities.User
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

data class GetUserDTO(
    val id:Int = 0,
    val userName:String,
    val email:String,
    val likedRecipes:Set<GetReducedRecipeDTO>? = emptySet(),
    val uploadedRecipes:Set<GetReducedRecipeDTO>?= emptySet(),
    val followedUsers:Set<GetUserForGetRecipeDTO>?= emptySet(),
    val followers:Set<GetUserForGetRecipeDTO>?= emptySet(),
)