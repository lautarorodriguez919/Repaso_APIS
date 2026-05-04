package com.example.apilistapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class SWCharacterEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val authors: String,
    val publisher: String,
    val publishedDate: String,
    val description: String,
    val pageCount: Int,
    val categories: String,
    val averageRating: Double,
    val language: String,
    val thumbnail: String
)