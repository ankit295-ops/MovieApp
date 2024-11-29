package com.example.movieapp.service

import com.example.movieapp.constants.Constants
import com.example.movieapp.interfaces.ApiInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    companion object {
        private val retrofit by lazy {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .client(OkHttpClient())
                .build()
        }

        val commonApis by lazy {
            retrofit.create(ApiInterface::class.java)
        }
    }
}