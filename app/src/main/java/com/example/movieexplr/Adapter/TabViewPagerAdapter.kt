package com.example.movieexplr.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.movieexplr.Model.Genre
import com.example.movieexplr.View.MovieListFragment

class TabViewPagerAdapter(private val genres: List<Genre>, fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = genres.size
    override fun getItem(position: Int): Fragment = MovieListFragment(genres[position])
    override fun getPageTitle(position: Int): CharSequence? = genres[position].name
}