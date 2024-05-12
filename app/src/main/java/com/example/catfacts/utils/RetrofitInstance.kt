package com.example.catfacts.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val Base = "https://catfact.ninja"
    val api : ApiInterface by lazy {
        Retrofit.Builder().baseUrl(Base)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiInterface :: class.java)
    }

}