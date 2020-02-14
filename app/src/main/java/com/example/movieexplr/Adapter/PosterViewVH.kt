package com.example.movieexplr.Adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.movieexplr.Model.MovieData
import com.example.movieexplr.View.PosterView

class PosterViewVH(private val view: PosterView) : RecyclerView.ViewHolder(view) {
    fun onBind(data: MovieData?) = view.updateView(data)
}


