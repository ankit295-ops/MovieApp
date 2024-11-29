package com.example.movieapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.adapters.MovieListAdapter
import com.example.movieapp.constants.Constants
import com.example.movieapp.databinding.FragmentNowPlayingBinding
import com.example.movieapp.repository.CommonRepository
import com.example.movieapp.viewmodel.ViewModels
import com.example.movieapp.viewmodel.ViewModelsFactory

class NowPlayingFragment : Fragment() {

    private lateinit var mBinding: FragmentNowPlayingBinding
    private lateinit var viewModel: ViewModels
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var repository: CommonRepository
    private var page = 1
    private var maxPages = 0
    private var pageName = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_now_playing, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieListAdapter = MovieListAdapter(requireContext(), arrayListOf())
        mBinding.rvMovieList.layoutManager = GridLayoutManager(requireContext(), 3)
        mBinding.rvMovieList.adapter = movieListAdapter

        repository = CommonRepository()

        viewModel = ViewModelProvider(
            this, ViewModelsFactory(repository)
        )[ViewModels::class.java]

        initObserver()

        callApi()

        mBinding.rvMovieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (page < maxPages) {
                    page++
                    mBinding.pbLoading.visibility = View.VISIBLE
                    callApi()
                }
            }
        })
    }

    private fun initObserver() {
        viewModel.nowPlayingLiveData.observe(viewLifecycleOwner) {
            mBinding.pbLoading.visibility = View.GONE
            if (it != null && it.results.size > 0) {

                movieListAdapter.addData(it.results)
                maxPages = it.total_pages
            }
        }
    }

    private fun callApi() {
        viewModel.fetchMoviesList(Constants.nowPlayingMoviesUrl, page)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            NowPlayingFragment()
    }
}