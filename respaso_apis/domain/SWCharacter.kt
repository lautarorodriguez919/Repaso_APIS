package com.example.apilistapp.domain

data class SWCharacter(
    val id: String,
    val title: String,
    val authors: List<String>,
    val publisher: String,
    val publishedDate: String,
    val description: String,
    val pageCount: Int,
    val categories: List<String>,
    val averageRating: Double,
    val language: String,
    val thumbnail: String
)