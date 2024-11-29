package com.example.movieapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityTrailerBinding
import com.example.movieapp.models.VideoTrailer
import com.example.movieapp.repository.CommonRepository
import com.example.movieapp.viewmodel.ViewModels
import com.example.movieapp.viewmodel.ViewModelsFactory

class TrailerActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityTrailerBinding
    private var videoId = ""
    private lateinit var viewModel: ViewModels
    private lateinit var repository: CommonRepository
    private var movie_id = ""
    private lateinit var videoTrailer: VideoTrailer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_trailer)

        if (intent != null) {
            if (intent.hasExtra("movie_id")) {
                movie_id = intent.getStringExtra("movie_id")!!
            }
        }

        repository = CommonRepository()

        viewModel = ViewModelProvider(
            this, ViewModelsFactory(repository)
        )[ViewModels::class.java]

        initObserver()

        viewModel.fetchMovieTrailer(movie_id)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpIFrame() {
        val html = "<iframe width=\"100%\" height=\"100%\"src=\"https://www.youtube.com/embed/$videoId\"frameborder=\"0\" allowfullscreen></iframe>"
        mBinding.run { wvVideo.settings.javaScriptEnabled = true }
        mBinding.wvVideo.webChromeClient = WebChromeClient()
        mBinding.wvVideo.loadData(html, "text/html", "utf-8")
    }

    private fun initObserver() {
        viewModel.movieTrailerLiveData.observe(this) {
            if (it != null) {
                videoTrailer = it
                if (videoTrailer.results.size > 0) {
                    for (i in videoTrailer.results) {
                        if (i.type == "Trailer") {
                            videoId = i.key
                            break
                        }
                    }
                }
                setUpIFrame()
            }
        }
    }
}