package com.example.movieapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentAboutMovieBinding
import com.example.movieapp.databinding.FragmentUpcomingMoviesBinding
import com.example.movieapp.models.MovieDetailsModel

class AboutMovieFragment : Fragment() {

    private lateinit var mBinding: FragmentAboutMovieBinding
    private lateinit var movieDetailsModel: MovieDetailsModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_movie, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            if (requireArguments().containsKey("movie_details")) {
                movieDetailsModel = requireArguments().getParcelable("movie_details")!!
                mBinding.tvMovieDescription.text = movieDetailsModel.overview
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AboutMovieFragment()
    }
}