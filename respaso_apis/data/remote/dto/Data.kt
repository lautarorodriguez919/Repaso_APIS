package com.example.apilistapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("items")
    val items: List<SWCharacterDto>? = null,
    @SerializedName("totalItems")
    val totalItems: Int = 0
)