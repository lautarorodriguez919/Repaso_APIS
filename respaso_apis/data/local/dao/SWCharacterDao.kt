package com.example.apilistapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.apilistapp.data.local.entity.SWCharacterEntity

@Dao
interface SWCharacterDao {
    @Query("SELECT * FROM favorites")
    suspend fun getAllFavorites(): List<SWCharacterEntity>

    @Query("SELECT * FROM favorites WHERE id = :bookId")
    suspend fun getFavoriteById(bookId: String): SWCharacterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(book: SWCharacterEntity)

    @Delete
    suspend fun deleteFavorite(book: SWCharacterEntity)

    @Query("DELETE FROM favorites")
    suspend fun deleteAllFavorites()
}