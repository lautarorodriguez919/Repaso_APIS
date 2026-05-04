package com.example.apilistapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SWCharacterDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("volumeInfo")
    val volumeInfo: VolumeInfoDto
)

data class VolumeInfoDto(
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("authors")
    val authors: List<String>? = null,
    @SerializedName("publisher")
    val publisher: String? = null,
    @SerializedName("publishedDate")
    val publishedDate: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("pageCount")
    val pageCount: Int? = null,
    @SerializedName("categories")
    val categories: List<String>? = null,
    @SerializedName("averageRating")
    val averageRating: Double? = null,
    @SerializedName("language")
    val language: String? = null,
    @SerializedName("imageLinks")
    val imageLinks: ImageLinksDto? = null
)

data class ImageLinksDto(
    @SerializedName("thumbnail")
    val thumbnail: String? = null,
    @SerializedName("smallThumbnail")
    val smallThumbnail: String? = null
)