package com.example.movieexplr.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieexplr.Network.Repo

@Suppress("UNCHECKED_CAST")
class MovieExplrViewModelFactory(private val context: Context)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieExplrViewModel::class.java)) {
            val repo = Repo(context)
            return MovieExplrViewModel(repo) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class type")
    }
}