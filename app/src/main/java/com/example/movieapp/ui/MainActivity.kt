package com.example.movieapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.adapters.DynamicFragmentAdapter
import com.example.movieapp.adapters.MovieListAdapter
import com.example.movieapp.constants.Constants
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.models.MovieListModel
import com.example.movieapp.repository.CommonRepository
import com.example.movieapp.viewmodel.ViewModels
import com.example.movieapp.viewmodel.ViewModelsFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var viewModel: ViewModels
    private lateinit var trendingAdapter: MovieListAdapter
    private var trendingList = arrayListOf<MovieListModel>()
    private lateinit var repository: CommonRepository
    private val tabList =
        arrayListOf("Now Playing Movies", "Popular Movies", "Top Rated Movies", "Upcoming Movies")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initViews()

        trendingAdapter = MovieListAdapter(this, trendingList)
        mBinding.rvTrending.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        mBinding.rvTrending.adapter = trendingAdapter

        repository = CommonRepository()

        viewModel = ViewModelProvider(
            this, ViewModelsFactory(repository)
        )[ViewModels::class.java]

        initObserver()

        viewModel.fetchMoviesList(Constants.trendingMoviesUrl)
    }

    private fun initViews() {
        mBinding.movieListViewPager.setOffscreenPageLimit(5)
        mBinding.movieListViewPager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                mBinding.tlCategory
            )
        )

        mBinding.tlCategory.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mBinding.movieListViewPager.setCurrentItem(tab!!.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        setDynamicFragmentToTabLayout()
    }

    private fun setDynamicFragmentToTabLayout() {
        for (i in tabList) {
            mBinding.tlCategory.addTab(mBinding.tlCategory.newTab().setText(i))
        }

        val mDynamicFragmentAdapter =
            DynamicFragmentAdapter(supportFragmentManager, mBinding.tlCategory.tabCount)

        mBinding.movieListViewPager.setAdapter(mDynamicFragmentAdapter)

        mBinding.movieListViewPager.setCurrentItem(0)
    }

    private fun initObserver() {
        viewModel.trendingListLiveData.observe(this) {
            if (it != null && it.results.size > 0) {
                if (it.results.size <= 5) {
                    trendingAdapter.updateData(it.results)
                } else {
                    it.results.subList(6, it.results.size).clear()
                    trendingAdapter.updateData(it.results)
                }
            }
        }
    }
}