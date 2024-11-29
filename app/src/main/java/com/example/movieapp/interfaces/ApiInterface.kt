package com.example.movieapp.interfaces

import com.example.movieapp.constants.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiInterface {
    @GET
    suspend fun getMoviesList(
        @Url url: String,
        @Query("language") language: String,
        @Query("api_key") api_key: String = Constants.API_KEY,
        @Query("page") page: Int = 1
    ): Response<Map<String, Any>>

    @GET
    suspend fun getMoviesDetailsAndCast(
        @Url url: String,
        @Query("language") language: String = "en-US",
        @Query("api_key") api_key: String = Constants.API_KEY,
    ): Response<Map<String, Any>>
}