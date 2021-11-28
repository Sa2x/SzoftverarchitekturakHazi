package com.example.backend.dto

import org.springframework.web.multipart.MultipartFile

data class CreateRecipeDTO(
    val file:MultipartFile?,
    val name:String?,
    val description:String?
)