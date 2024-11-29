package com.example.movieapp.models

data class MovieParentClass(
    val total_pages: Int = 0,
    val results: ArrayList<MovieListModel> = arrayListOf()
)

data class MovieListModel(
    val id: Int = 0,
    val poster_path: String = ""
)
