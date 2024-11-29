package com.example.movieapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.R
import com.example.movieapp.adapters.CastListAdapter
import com.example.movieapp.databinding.FragmentCastBinding
import com.example.movieapp.models.MovieCredits

class CastFragment : Fragment() {

    private lateinit var mBinding: FragmentCastBinding
    private lateinit var movieCredits: MovieCredits

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cast, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            if (requireArguments().containsKey("movie_cast")) {
                movieCredits = requireArguments().getParcelable("movie_cast")!!
            }
        }

        if (movieCredits.cast.size > 0) {
            mBinding.rvCast.layoutManager = GridLayoutManager(requireContext(), 2)
            mBinding.rvCast.adapter = CastListAdapter(requireContext(), movieCredits.cast)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CastFragment()
    }
}