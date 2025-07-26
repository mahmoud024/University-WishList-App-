package com.example.universitywishlistapp.data.api

import com.example.universitywishlistapp.data.model.University
import retrofit2.http.GET
import retrofit2.http.Query

interface UniversityApi {
    @GET("search")
    suspend fun searchUniversities(
        @Query("name") name: String
    ): List<University>

    @GET("search")
    suspend fun getAllUniversities(): List<University>

}