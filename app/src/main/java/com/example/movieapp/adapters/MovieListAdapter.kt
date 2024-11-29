package com.example.movieapp.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.constants.Constants
import com.example.movieapp.databinding.SingleRowTrendingBinding
import com.example.movieapp.models.MovieListModel
import com.example.movieapp.ui.MovieDetailsActivity
import com.squareup.picasso.Picasso

class MovieListAdapter(
    private val mContext: Context,
    private var movieList: ArrayList<MovieListModel>
) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    class ViewHolder(val binding: SingleRowTrendingBinding) : RecyclerView.ViewHolder(binding.root)

    private var hideNumber = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mBinding: SingleRowTrendingBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.single_row_trending,
            parent,
            false
        )
        return ViewHolder(mBinding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(Constants.IMAGE_BASE_URL + movieList[position].poster_path)
            .config(Bitmap.Config.RGB_565).error(R.drawable.no_image).into(holder.binding.ivPoster)
        Log.e("image url", Constants.IMAGE_BASE_URL + movieList[position].poster_path)
        if (hideNumber) {
            holder.binding.tvId.visibility = View.GONE
        } else {
            holder.binding.tvId.visibility = View.VISIBLE
            holder.binding.tvId.text = "${position + 1}"
        }

        holder.binding.cvMovieBanner.setOnClickListener {
            mContext.startActivity(
                Intent(mContext, MovieDetailsActivity::class.java)
                    .putExtra("movie_id", movieList[position].id.toString())
            )
        }
    }

    fun updateData(newList: ArrayList<MovieListModel>) {
        hideNumber = false
        movieList = newList
        notifyDataSetChanged()
    }

    fun addData(listItems: ArrayList<MovieListModel>) {
        hideNumber = true
        val size = movieList.size
        movieList.addAll(listItems)
        val sizeNew = movieList.size
        notifyItemRangeChanged(size, sizeNew)
    }
}