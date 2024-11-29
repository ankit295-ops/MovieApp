package com.example.movieapp.models

data class VideoTrailer(
    val id: Int = 0,
    val results: ArrayList<Trailer> = arrayListOf()
)

data class Trailer(
    val iso_639_1: String = "",
    val iso_3166_1: String = "",
    val name: String = "",
    val key: String = "",
    val site: String = "",
    val size: Int = 0,
    val type: String = "",
    val official: Boolean = false,
    val published_at: String = "",
    val id: String = ""
)
