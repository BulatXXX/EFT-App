package com.singularity.eft_app.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ItemApi {
    @Headers("Content-Type: application/json")
    @GET("/graphql")
    suspend fun getItems(
        @Query("query") query: String
    ): Response<String>
}