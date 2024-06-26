package com.example.kotlinpracticemirea.retrofit

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ItemApi {
    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun getItems(@Body body: String): Response<String>
}