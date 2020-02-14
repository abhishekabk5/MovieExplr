package com.example.movieexplr.View

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.movieexplr.Model.MovieData
import com.example.movieexplr.R
import kotlinx.android.synthetic.main.item_list_movie.view.*

class PosterView(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
    : LinearLayout(context, attrs, defStyleAttr) {

    private val requestManager = Glide.with(context)

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        LayoutInflater.from(context).inflate(R.layout.item_list_movie, this, true)
    }

    fun updateView(data: MovieData?) = data?.let {
        title_text.text = data.title
        rating_text.text = "${data.rating}/10.0"
        overview_text.text = data.overview
        updateImageView(data.posterPath)
        this.requestLayout()
    }

    private fun updateImageView(posterPath: String) {
        poster_image.setImageResource(R.drawable.poster_placeholder)
//        Glide.with(context)
        requestManager
            .load(buildImageUrl(posterPath))
            .placeholder(R.drawable.poster_placeholder)
            .into(poster_image)
    }

    private fun buildImageUrl(posterPath: String)
            : String = images_base_url + poster_size_w342 + posterPath

    companion object {
        private const val images_base_url = "https://image.tmdb.org/t/p"
        private const val poster_size_w185 = "/w185"
        private const val poster_size_w342 = "/w342"
    }
}