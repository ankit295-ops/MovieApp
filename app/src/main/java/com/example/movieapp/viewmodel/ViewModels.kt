package com.example.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.MovieListModel
import com.example.movieapp.repository.CommonRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ViewModels(private val repository: CommonRepository): ViewModel() {

    val trendingListLiveData = repository.trendingListLiveData
    val nowPlayingLiveData = repository.nowPlayingLiveData
    val popularMoviesLiveData = repository.popularMoviesLiveData
    val topRatedMoviesLiveData = repository.topRatedMoviesLiveData
    val upComingMoviesLiveData = repository.upComingMoviesLiveData
    val movieDetailsLiveData = repository.movieDetailsLiveData
    val movieCreditsLiveData = repository.movieCreditsLiveData
    val movieTrailerLiveData = repository.movieTrailerLiveData

    fun fetchMoviesList(url: String, page: Int = 1) = viewModelScope.launch {
            repository.getMoviesList(url, page)
        }

    fun fetchMovieDetails(movieId: String) = viewModelScope.launch {
            repository.getMoviesDetails(movieId)
        }

    fun fetchMovieCast(movieId: String) = viewModelScope.launch {
            repository.getMoviesCast(movieId)
        }

    fun fetchMovieTrailer(movieId: String) = viewModelScope.launch {
            repository.getMoviesTrailer(movieId)
        }
}

class ViewModelsFactory(private val repository: CommonRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ViewModels(repository) as T
    }
}