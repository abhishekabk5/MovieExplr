package com.example.movieexplr.ViewModel


import android.util.Log
import android.util.SparseArray
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.movieexplr.Adapter.MovieListAdapter
import com.example.movieexplr.Model.Genre
import com.example.movieexplr.Model.GenreListResponse
import com.example.movieexplr.Model.MovieData
import com.example.movieexplr.Model.MovieListResponse
import com.example.movieexplr.Network.Repo
import com.example.movieexplr.Network.ResponseCallback

class MovieExplrViewModel(private val repo: Repo) : ViewModel() {

    private var genreList = MutableLiveData<List<Genre>>()

    private var mMovieList = SparseArray<MutableLiveData<List<MovieData>>>()
    fun getMovieListLiveData(genreId: Int): LiveData<List<MovieData>> {
        if(mMovieList[genreId] == null)
            mMovieList.append(genreId, MutableLiveData())
        return mMovieList[genreId]
    }

    private fun getListSize(genreId: Int): Int = mMovieList[genreId]?.value?.size ?: 0

    fun getGenreList(): LiveData<List<Genre>> {

        if((genreList.value?.size ?: 0) < 1)
        repo.getGenreList(object: ResponseCallback<GenreListResponse>() {
            override fun onSuccess(response: GenreListResponse) {
                genreList.value = response.list
                Log.d("Callback", "Genre List Size: ${response.list.size}")
            }
        })

        return genreList
    }

    fun getMovieList(genreId: Int) {
        val page = (getListSize(genreId) / PAGE_SIZE) + 1
        repo.getMoviesByGenre(genreId,
            object: ResponseCallback<MovieListResponse>() {
                override fun onSuccess(response: MovieListResponse) {
                    Log.d("movieList Update", "Genre: $genreId, Page: $page, now size: ${getListSize(genreId)}")
                    if(page == response.page) {
                        mMovieList[genreId].value = mMovieList[genreId].value.orEmpty()
                            .plus(response.results)
                    }
                }
            }, page)
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}

