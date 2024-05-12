package com.example.catfacts.utils

import com.example.catfacts.Data.CatFacts
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("/fact")
    suspend fun getRandomFact() : Response<CatFacts>
}