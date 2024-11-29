package com.example.movieapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.constants.Constants
import com.example.movieapp.databinding.CastLayoutBinding
import com.example.movieapp.models.Cast
import com.squareup.picasso.Picasso

class CastListAdapter(
    private val mContext: Context,
    private val castList: ArrayList<Cast>
) : RecyclerView.Adapter<CastListAdapter.ViewHolder>() {

    class ViewHolder(val binding: CastLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mBinding: CastLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.cast_layout,
            parent,
            false
        )
        return ViewHolder(mBinding)
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(Constants.IMAGE_BASE_URL + castList[position].profile_path)
            .error(R.drawable.ic_person).into(holder.binding.ivCast)
        holder.binding.tvName.text = castList[position].name
    }
}