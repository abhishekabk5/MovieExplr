package com.example.movieexplr.Model

import com.google.gson.annotations.SerializedName

data class MovieData(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("genre_ids")
    val genres: List<Int>,

    @SerializedName("vote_average")
    val rating: Float,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("poster_path")
    val posterPath: String
)