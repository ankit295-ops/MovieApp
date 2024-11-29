package com.example.movieapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.movieapp.constants.Constants
import com.example.movieapp.models.MovieCredits
import com.example.movieapp.models.MovieDetailsModel
import com.example.movieapp.models.MovieParentClass
import com.example.movieapp.models.VideoTrailer
import com.example.movieapp.service.RetrofitService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class CommonRepository {

    val trendingListLiveData: MutableLiveData<MovieParentClass> = MutableLiveData()
    val nowPlayingLiveData: MutableLiveData<MovieParentClass> = MutableLiveData()
    val popularMoviesLiveData: MutableLiveData<MovieParentClass> = MutableLiveData()
    val topRatedMoviesLiveData: MutableLiveData<MovieParentClass> = MutableLiveData()
    val upComingMoviesLiveData: MutableLiveData<MovieParentClass> = MutableLiveData()
    val movieDetailsLiveData: MutableLiveData<MovieDetailsModel> = MutableLiveData()
    val movieCreditsLiveData: MutableLiveData<MovieCredits> = MutableLiveData()
    val movieTrailerLiveData: MutableLiveData<VideoTrailer> = MutableLiveData()

    suspend fun getMoviesList(
        url: String,
        pageNo: Int
    ): MutableLiveData<MovieParentClass> {
        try {
            val response = RetrofitService.commonApis.getMoviesList(url, "en-US", page = pageNo)
            if (response.isSuccessful) {
                val responseBody = response.body()
                val responseJson = responseBody?.let { JSONObject(it) }
                val parentClass: MovieParentClass = Gson().fromJson(
                    responseJson.toString(),
                    TypeToken.getParameterized(
                        MovieParentClass::class.java
                    ).type
                )
                when (url) {
                    Constants.trendingMoviesUrl -> trendingListLiveData.postValue(parentClass)
                    Constants.nowPlayingMoviesUrl -> nowPlayingLiveData.postValue(parentClass)
                    Constants.popularMoviesUrl -> popularMoviesLiveData.postValue(parentClass)
                    Constants.topRatedMoviesUrl -> topRatedMoviesLiveData.postValue(parentClass)
                    Constants.upcomingMoviesUrl -> upComingMoviesLiveData.postValue(parentClass)
                }
            } else {
                val movieList = MovieParentClass()
                when (url) {
                    Constants.trendingMoviesUrl -> trendingListLiveData.postValue(movieList)
                    Constants.nowPlayingMoviesUrl -> nowPlayingLiveData.postValue(movieList)
                    Constants.popularMoviesUrl -> popularMoviesLiveData.postValue(movieList)
                    Constants.topRatedMoviesUrl -> topRatedMoviesLiveData.postValue(movieList)
                    Constants.upcomingMoviesUrl -> upComingMoviesLiveData.postValue(movieList)
                }
            }
        } catch (e: Exception) {
            Log.e("error", e.message!!)
            val movieList = MovieParentClass()
            when (url) {
                Constants.trendingMoviesUrl -> trendingListLiveData.postValue(movieList)
                Constants.nowPlayingMoviesUrl -> nowPlayingLiveData.postValue(movieList)
                Constants.popularMoviesUrl -> popularMoviesLiveData.postValue(movieList)
                Constants.topRatedMoviesUrl -> topRatedMoviesLiveData.postValue(movieList)
                Constants.upcomingMoviesUrl -> upComingMoviesLiveData.postValue(movieList)
            }
        }
        return when (url) {
            Constants.trendingMoviesUrl -> trendingListLiveData
            Constants.nowPlayingMoviesUrl -> nowPlayingLiveData
            Constants.popularMoviesUrl -> popularMoviesLiveData
            Constants.topRatedMoviesUrl -> topRatedMoviesLiveData
            Constants.upcomingMoviesUrl -> upComingMoviesLiveData
            else -> trendingListLiveData
        }
    }

    suspend fun getMoviesDetails(
        movieId: String
    ): MutableLiveData<MovieDetailsModel> {
        try {
            val response =
                RetrofitService.commonApis.getMoviesDetailsAndCast(Constants.movieDetailsUrl + movieId)
            if (response.isSuccessful) {
                val responseBody = response.body()
                val responseJson = responseBody?.let { JSONObject(it) }
                val parentClass: MovieDetailsModel = Gson().fromJson(
                    responseJson.toString(),
                    TypeToken.getParameterized(
                        MovieDetailsModel::class.java
                    ).type
                )
                movieDetailsLiveData.postValue(parentClass)
            } else {
                val movieDetails = MovieDetailsModel()
                movieDetailsLiveData.postValue(movieDetails)
            }
        } catch (e: Exception) {
            Log.e("error", e.message!!)
            val movieDetails = MovieDetailsModel()
            movieDetailsLiveData.postValue(movieDetails)
        }
        return movieDetailsLiveData
    }

    suspend fun getMoviesCast(
        movieId: String
    ): MutableLiveData<MovieCredits> {
        try {
            val response =
                RetrofitService.commonApis.getMoviesDetailsAndCast(Constants.movieDetailsUrl + movieId + Constants.movieCreditsUrl)
            if (response.isSuccessful) {
                val responseBody = response.body()
                val responseJson = responseBody?.let { JSONObject(it) }
                val parentClass: MovieCredits = Gson().fromJson(
                    responseJson.toString(),
                    TypeToken.getParameterized(
                        MovieCredits::class.java
                    ).type
                )
                movieCreditsLiveData.postValue(parentClass)
            } else {
                val movieCredits = MovieCredits()
                movieCreditsLiveData.postValue(movieCredits)
            }
        } catch (e: Exception) {
            Log.e("error", e.message!!)
            val movieCredits = MovieCredits()
            movieCreditsLiveData.postValue(movieCredits)
        }
        return movieCreditsLiveData
    }

    suspend fun getMoviesTrailer(
        movieId: String
    ): MutableLiveData<VideoTrailer> {
        try {
            val response =
                RetrofitService.commonApis.getMoviesDetailsAndCast(Constants.movieDetailsUrl + movieId + Constants.movieTrailerUrl)
            if (response.isSuccessful) {
                val responseBody = response.body()
                val responseJson = responseBody?.let { JSONObject(it) }
                val parentClass: VideoTrailer = Gson().fromJson(
                    responseJson.toString(),
                    TypeToken.getParameterized(
                        VideoTrailer::class.java
                    ).type
                )
                movieTrailerLiveData.postValue(parentClass)
            } else {
                val movieVideoTrailer = VideoTrailer()
                movieTrailerLiveData.postValue(movieVideoTrailer)
            }
        } catch (e: Exception) {
            Log.e("error", e.message!!)
            val movieVideoTrailer = VideoTrailer()
            movieTrailerLiveData.postValue(movieVideoTrailer)
        }
        return movieTrailerLiveData
    }
}