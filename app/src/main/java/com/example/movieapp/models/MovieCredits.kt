package com.example.movieapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieCredits(
    val id: Int = 0,
    val cast: ArrayList<Cast> = arrayListOf()
): Parcelable

@Parcelize
data class Cast(
    val id: Int = 0,
    val name: String = "",
    val profile_path: String = "",
): Parcelable
