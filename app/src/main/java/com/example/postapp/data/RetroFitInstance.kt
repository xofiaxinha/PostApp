package com.example.postapp.data.models

import com.example.postapp.data.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitInstance {
    private const val BASE_URL = "https://my-api-0362.onrender.com/ "

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}