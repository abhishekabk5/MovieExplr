package com.example.movieexplr.Model

import com.google.gson.annotations.SerializedName

data class GenreListResponse(
    @SerializedName("genres")
    val list: List<Genre>
)

data class Genre(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String
)