package com.example.apilistapp.data.remote

import com.example.apilistapp.data.remote.dto.Data
import com.example.apilistapp.data.remote.dto.SWCharacterDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 40,
        @Query("printType") printType: String = "books"
    ): Data

    @GET("volumes/{volumeId}")
    suspend fun getBookById(
        @Path("volumeId") volumeId: String
    ): SWCharacterDto

    companion object {
        private const val BASE_URL = "https://www.googleapis.com/books/v1/"
        private const val API_KEY = "AIzaSyD9CR76uA7XmbNxzK5r8rPNWqK-qOU3PE0"

        fun create(): ApiInterface {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor { chain ->
                    val url = chain.request().url.newBuilder()
                        .addQueryParameter("key", API_KEY)
                        .build()
                    chain.proceed(
                        chain.request().newBuilder().url(url).build()
                    )
                }
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiInterface::class.java)
        }
    }
}