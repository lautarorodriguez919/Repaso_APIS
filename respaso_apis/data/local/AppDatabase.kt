package com.example.apilistapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.apilistapp.data.local.dao.SWCharacterDao
import com.example.apilistapp.data.local.entity.SWCharacterEntity

@Database(
    entities = [SWCharacterEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): SWCharacterDao
}