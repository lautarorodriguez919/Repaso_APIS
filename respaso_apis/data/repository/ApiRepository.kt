package com.example.apilistapp.data.repository

import com.example.apilistapp.data.mapper.toDomain
import com.example.apilistapp.data.remote.ApiInterface
import com.example.apilistapp.domain.SWCharacter

class ApiRepository {
    private val api = ApiInterface.create()

    suspend fun searchBooks(query: String): List<SWCharacter> =
        api.searchBooks(query).items?.map { it.toDomain() } ?: emptyList()

    suspend fun getBookById(id: String): SWCharacter =
        api.getBookById(id).toDomain()
}