package com.example.movieapp.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.movieapp.fragments.AboutMovieFragment
import com.example.movieapp.fragments.CastFragment
import com.example.movieapp.fragments.NowPlayingFragment
import com.example.movieapp.fragments.PopularMoviesFragment
import com.example.movieapp.fragments.TopRatedMoviesFragment
import com.example.movieapp.fragments.UpcomingMoviesFragment
import com.example.movieapp.models.MovieCredits
import com.example.movieapp.models.MovieDetailsModel

class DynamicFragmentAdapter(
    private val fm: FragmentManager,
    private val numOfTabs: Int,
    private val type: String = "",
    private val movieDetailsModel: MovieDetailsModel = MovieDetailsModel(),
    private val movieCredits: MovieCredits = MovieCredits()
) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return numOfTabs
    }

    override fun getItem(position: Int): Fragment {
        val frag = if (type == "description") {
            when (position) {
                0 -> AboutMovieFragment.newInstance()
                else -> CastFragment.newInstance()
            }
        } else {
            when (position) {
                0 -> NowPlayingFragment.newInstance()
                1 -> PopularMoviesFragment.newInstance()
                2 -> TopRatedMoviesFragment.newInstance()
                else -> UpcomingMoviesFragment.newInstance()
            }
        }
        if (type == "description") {
            val bundle = Bundle()
            if (position == 0) {
                bundle.putParcelable("movie_details", movieDetailsModel)
            } else {
                bundle.putParcelable("movie_cast", movieCredits)
            }
            frag.arguments = bundle
        }
        return frag
    }
}