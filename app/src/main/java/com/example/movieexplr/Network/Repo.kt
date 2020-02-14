package com.example.movieexplr.Network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.movieexplr.Model.GenreListResponse
import com.example.movieexplr.Model.MovieListResponse
import com.google.gson.Gson

class Repo(context: Context) {

    companion object {
        private const val base_url = "https://api.themoviedb.org/3"
        private const val api_key = "6b728ca4a051993d54c7faccfa60c07d"
        private const val params = "api_key=$api_key"
    }

    private val gson = Gson()
    private val queue = Volley.newRequestQueue(context.applicationContext)

    fun getGenreList(callback: ResponseCallback<GenreListResponse>) {
        val endpoint = "genre/movie/list"
        val req = JsonObjectRequest(
            Request.Method.GET,
            buildUrl(endpoint, ""),
            null,
            Response.Listener{ response ->
                val list = gson.fromJson(response.toString(), GenreListResponse::class.java)
                callback.onSuccess(list)

        }, Response.ErrorListener{ error ->
                callback.onError(error)
        })

        queue.add(req)
    }

    fun getMoviesByGenre(genreId: Int, callback: ResponseCallback<MovieListResponse>, page: Int = 1) {
        val endpoint = "discover/movie"
        val params = (if(genreId != 0) "with_genres=$genreId&" else "") + "page=$page"

        val req = JsonObjectRequest(
            Request.Method.GET,
            buildUrl(endpoint, params),
            null,
            Response.Listener { response ->
                val parsed = gson
                    .fromJson(response.toString(), MovieListResponse::class.java)
                callback.onSuccess(parsed)

            }, Response.ErrorListener { error ->
                callback.onError(error)
            })

        queue.add(req)
    }

    private fun buildUrl(endpoint: String, queryParams: String) : String {
        var url = base_url
        if(endpoint != "")
            url += "/$endpoint"

        var par = params
        if(queryParams != "")
            par += "&$queryParams"

        if(par != "")
            url += "?$par"

        Log.i("Api", "Url: $url")

        return url
    }
}
