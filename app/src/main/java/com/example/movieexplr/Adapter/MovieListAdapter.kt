package com.example.movieexplr.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieexplr.Model.MovieData
import com.example.movieexplr.R
import com.example.movieexplr.View.PosterView

abstract class MovieListAdapter(private val recyclerView: RecyclerView, val genreId: Int)
    : ListAdapter<MovieData?, RecyclerView.ViewHolder>(MovieDataDiffCB()) {

    private val isLoading
        get() = ((itemCount > 0) && (currentList[itemCount - 1] == null))

    init {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!isLoading && newState == RecyclerView.SCROLL_STATE_DRAGGING && !recyclerView.canScrollVertically(1)) {
                    showLoading()
                    onLoadMore()
//
//                    recyclerView.smoothScrollToPosition(itemCount - 1)
                }
            }
        })

        val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int =
                if(getItemViewType(position) == VIEW_TYPE_ITEM) 1 else gridLayoutManager.spanCount
        }
    }

    override fun getItemViewType(position: Int): Int =
        if(currentList[position] == null) VIEW_TYPE_PROGRESS else VIEW_TYPE_ITEM

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == VIEW_TYPE_PROGRESS) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false)
            return object : RecyclerView.ViewHolder(view) {}
        }
        else PosterViewVH(PosterView(parent.context))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is PosterViewVH)
            holder.onBind(currentList[position])
    }

    abstract fun onLoadMore()

    fun showLoading() {
        if(!isLoading)
            submitList(currentList.plus(listOf(null)))
    }

    override fun submitList(list: List<MovieData?>?) {
        Log.d("posterList Update", "Genre: $genreId, oldSize: ${currentList.size}, newSize: ${list?.size ?: 0}")
        super.submitList(list)
        if(list?.size ?: 0 == 0) {
            showLoading()
            onLoadMore()
        }
    }

    companion object {
        const val VIEW_TYPE_ITEM = 1
        const val VIEW_TYPE_PROGRESS = 99
    }

}

