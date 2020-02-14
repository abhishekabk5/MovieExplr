package com.example.movieexplr.Adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.movieexplr.Model.MovieData

class MovieDataDiffCB : DiffUtil.ItemCallback<MovieData?>() {
    override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData)
            : Boolean = (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
        return oldItem == newItem
    }
}