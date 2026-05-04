package com.example.apilistapp.data.repository

import com.example.apilistapp.APIListApplication
import com.example.apilistapp.data.mapper.toDomain
import com.example.apilistapp.data.mapper.toEntity
import com.example.apilistapp.domain.SWCharacter

class FavoriteRepository {
    private val dao = APIListApplication.database.characterDao()

    suspend fun saveAsFavorite(book: SWCharacter) =
        dao.insertFavorite(book.toEntity())

    suspend fun deleteFavorite(book: SWCharacter) =
        dao.deleteFavorite(book.toEntity())

    suspend fun deleteAllFavorites() =
        dao.deleteAllFavorites()

    suspend fun isFavorite(bookId: String): Boolean =
        dao.getFavoriteById(bookId) != null


    suspend fun getFavorites(): List<SWCharacter> =
        dao.getAllFavorites().map { it.toDomain() }
}