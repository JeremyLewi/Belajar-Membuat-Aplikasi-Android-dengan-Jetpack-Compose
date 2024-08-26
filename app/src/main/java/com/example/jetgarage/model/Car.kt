package com.example.jetgarage.model

data class Car(
    val id: Long,
    val image: Int,
    val title: String,
    val requiredPrice: Int,
    val description: String
)