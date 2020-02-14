package com.example.movieexplr.View


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.movieexplr.Adapter.MovieListAdapter
import com.example.movieexplr.Model.Genre
import com.example.movieexplr.R
import com.example.movieexplr.ViewModel.MovieExplrViewModel
import com.example.movieexplr.ViewModel.MovieExplrViewModelFactory
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MovieListFragment(val genre: Genre) : Fragment() {

    private lateinit var viewModel: MovieExplrViewModel
    private lateinit var adapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelProvider =
            ViewModelProvider(requireActivity(), MovieExplrViewModelFactory(requireContext()))
        viewModel = viewModelProvider[MovieExplrViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        adapter = object: MovieListAdapter(movie_list_recycler_view, genre.id) {
            override fun onLoadMore() {
                viewModel.getMovieList(genreId)
            }
        }
        movie_list_recycler_view.adapter = adapter
        adapter.submitList(emptyList())

        viewModel.getMovieListLiveData(genre.id).observe(viewLifecycleOwner, Observer { it?.let {
            adapter.submitList(it)
        }})

//        adapter.onLoadMore(1)
    }
}
