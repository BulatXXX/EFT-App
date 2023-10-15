package com.example.kotlinpracticemirea.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object ItemInstance {


    private const val BASE_URL: String = "https://api.tarkov.dev/graphql/"

    val ItemService: ItemApi by lazy {
            Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                //.addConverterFactory(GsonConverterFactory.create())
                .build().create(ItemApi::class.java)
        }

}