package com.example.hilt_retrofit.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.hilt_retrofit.R
import com.example.hilt_retrofit.databinding.ItemMoviesBinding
import com.example.hilt_retrofit.utils.Constants.POSTER_BASE_URL
import com.ezatpanah.hilt_retrofit_youtube.response.MoviesListResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesAdapter @Inject constructor() :
    PagingDataAdapter<MoviesListResponse.Result, MoviesAdapter.ViewHolder>(differCallback) {

    private lateinit var binding: ItemMoviesBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemMoviesBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }


    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: MoviesListResponse.Result) {
            binding.apply {
                tvMovieName.text = item.title
                tvMovieDateRelease.text = item.releaseDate
                tvRate.text = item.voteAverage.toString()
                val moviePosterURL = POSTER_BASE_URL + item?.posterPath
                imgMovie.load(moviePosterURL) {
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }
                tvLang.text = item.originalLanguage

                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((MoviesListResponse.Result) -> Unit)? = null

    fun setOnItemClickListener(listener: (MoviesListResponse.Result) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<MoviesListResponse.Result>() {
            override fun areItemsTheSame(
                oldItem: MoviesListResponse.Result,
                newItem: MoviesListResponse.Result
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MoviesListResponse.Result,
                newItem: MoviesListResponse.Result
            ): Boolean {
                return oldItem == newItem
            }
        }
    }


}