package com.example.movieapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailsModel(
    val backdrop_path: String = "",
    val genres: ArrayList<Genres> = arrayListOf(),
    val id: Int = 0,
    val original_title: String = "",
    val overview: String = "",
    val poster_path: String = "",
    val release_date: String = "",
    val runtime: Double = 0.0,
): Parcelable

@Parcelize
data class Genres(
    val id: Int = 0,
    val name: String = ""
): Parcelable
