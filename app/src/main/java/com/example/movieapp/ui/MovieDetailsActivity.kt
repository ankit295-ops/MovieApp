package com.example.movieapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.R
import com.example.movieapp.adapters.DynamicFragmentAdapter
import com.example.movieapp.constants.Constants
import com.example.movieapp.databinding.ActivityMovieDetailsBinding
import com.example.movieapp.models.MovieCredits
import com.example.movieapp.models.MovieDetailsModel
import com.example.movieapp.repository.CommonRepository
import com.example.movieapp.viewmodel.ViewModels
import com.example.movieapp.viewmodel.ViewModelsFactory
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import kotlin.time.Duration.Companion.minutes

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMovieDetailsBinding
    private val tabItems = arrayListOf("About Movie", "Cast")
    private var movie_id = ""
    private lateinit var viewModel: ViewModels
    private lateinit var repository: CommonRepository
    private lateinit var movieDetailsModel: MovieDetailsModel
    private lateinit var movieCastModel: MovieCredits

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)

        if (intent != null) {
            if (intent.hasExtra("movie_id")) {
                if (intent.getStringExtra("movie_id") != null) {
                    movie_id = intent.getStringExtra("movie_id")!!
                }
            }
        }

        repository = CommonRepository()

        viewModel = ViewModelProvider(
            this, ViewModelsFactory(repository)
        )[ViewModels::class.java]

        initObserver()

        viewModel.fetchMovieDetails(movie_id)

        mBinding.ivBack.setOnClickListener {
            finish()
        }

        mBinding.btnTrailer.setOnClickListener {
            val intent = Intent(this, TrailerActivity::class.java)
            intent.putExtra("movie_id", movie_id)
            startActivity(intent)
        }
    }

    private fun initViews() {
        mBinding.vpDescription.setOffscreenPageLimit(5)
        mBinding.vpDescription.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                mBinding.tlCategory
            )
        )

        mBinding.tlCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mBinding.vpDescription.setCurrentItem(tab!!.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        setDynamicFragmentToTabLayout()
    }

    private fun setDynamicFragmentToTabLayout() {
        for (i in tabItems) {
            mBinding.tlCategory.addTab(mBinding.tlCategory.newTab().setText(i))
        }

        val mDynamicFragmentAdapter =
            DynamicFragmentAdapter(
                supportFragmentManager,
                mBinding.tlCategory.tabCount,
                "description",
                movieDetailsModel,
                movieCastModel
            )

        mBinding.vpDescription.setAdapter(mDynamicFragmentAdapter)

        mBinding.vpDescription.setCurrentItem(0)
    }

    private fun initObserver() {
        viewModel.movieDetailsLiveData.observe(this) {
            if (it != null) {
                movieDetailsModel = it

                Picasso.get().load(Constants.IMAGE_BASE_URL + movieDetailsModel.backdrop_path).error(R.drawable.no_image).into(mBinding.ivBannerImage)
                Picasso.get().load(Constants.IMAGE_BASE_URL + movieDetailsModel.poster_path).error(R.drawable.no_image_white).into(mBinding.ivSmallImage)
                mBinding.tvMovieTitle.text = movieDetailsModel.original_title
                mBinding.tvDate.text = movieDetailsModel.release_date
                mBinding.tvGenres.text = movieDetailsModel.genres[0].name
                mBinding.tvDuration.text = movieDetailsModel.runtime.minutes.toComponents { hours, minutes, secends, milliseconds -> "${hours}:${minutes}:${secends}:${milliseconds}" }
            }

            viewModel.fetchMovieCast(movie_id)
        }

        viewModel.movieCreditsLiveData.observe(this) {
            if (it != null) {
                movieCastModel = it
            }
            initViews()
        }
    }
}