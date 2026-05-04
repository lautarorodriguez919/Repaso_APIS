package com.example.apilistapp

import android.app.Application
import androidx.room.Room
import com.example.apilistapp.data.local.AppDatabase

class APIListApplication : Application() {

    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "BooksDatabase"
        ).build()
    }
}