package com.example.pgr208_2023h.models

data class Product(
    val id: Int,
    val price: Double,
    val title: String,
    val image: String,
    val description: String,
    val category: String,
    val rating: Rating
)

data class Rating(val rate: Double, val count: Int)

