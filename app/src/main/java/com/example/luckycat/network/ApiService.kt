package com.example.luckycat.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.thecatapi.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("/v1/images/search")
    suspend fun getProperties(
        @Query("limit") size: Int,
        @Query("page") page: Int
    ): List<CatProperty>
}

object CatApi {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}