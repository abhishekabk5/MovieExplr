package com.example.movieexplr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.movieexplr.Adapter.TabViewPagerAdapter
import com.example.movieexplr.Model.Genre
import com.example.movieexplr.ViewModel.MovieExplrViewModel
import com.example.movieexplr.ViewModel.MovieExplrViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieExplrViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, MovieExplrViewModelFactory(this))
            .get(MovieExplrViewModel::class.java)

        viewModel.getGenreList().observe(this, Observer {
            val list = listOf(Genre(0, "All"))
                .plus(it?.subList(0, 4) ?: emptyList())

            tab_view_pager.adapter = TabViewPagerAdapter(list, supportFragmentManager)
        })

        tablayout_genres.setupWithViewPager(tab_view_pager)
    }
}
