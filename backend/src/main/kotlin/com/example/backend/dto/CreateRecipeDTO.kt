package com.example.backend.dto

import com.example.backend.entities.Diet
import org.springframework.web.multipart.MultipartFile
import javax.persistence.Lob

data class CreateRecipeDTO(
    val file:MultipartFile,
    val name:String,
    val description:String,
    val ingredients: Set<String>,
    val diets: Set<Diet>,
)