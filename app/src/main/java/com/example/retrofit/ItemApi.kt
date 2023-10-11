package com.example.retrofit

import com.example.kotlinpracticemirea.Item
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ItemApi {
    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun getItems(@Body body: String): Response<String>

    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun getItem(@Body body: String): Response<String>

}