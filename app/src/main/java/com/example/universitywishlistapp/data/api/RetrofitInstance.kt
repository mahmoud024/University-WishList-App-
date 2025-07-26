package com.example.universitywishlistapp.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: UniversityApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://universities.hipolabs.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UniversityApi::class.java)
    }
}