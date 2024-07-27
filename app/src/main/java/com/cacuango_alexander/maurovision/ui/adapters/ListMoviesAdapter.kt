package com.cacuango_alexander.maurovision.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cacuango_alexander.maurovision.R
import com.cacuango_alexander.maurovision.core.Constant
import com.cacuango_alexander.maurovision.databinding.ItemsMovieBinding
import com.cacuango_alexander.maurovision.ui.entities.MoviesInfoUI

// Define la interfaz OnMovieClickListener
interface OnMovieClickListener {
    fun onMovieClick(movie: MoviesInfoUI)
}

class ListMoviesAdapter(private val itemClick: OnMovieClickListener) :
    ListAdapter<MoviesInfoUI, ListMoviesAdapter.MovieVH>(DiffUtilMoviesCallback) {

    class MovieVH(view: View) : RecyclerView.ViewHolder(view) {
        private var binding: ItemsMovieBinding = ItemsMovieBinding.bind(view)

        fun render(item: MoviesInfoUI) {
            Log.d("ListMoviesAdapter", "Loading image: ${Constant.URL_IMG + item.poster_path}")
            binding.imgPoster.load(Constant.URL_IMG + item.poster_path)
            binding.circularProgress.maxProgress = 10.0
            binding.circularProgress.setCurrentProgress(item.vote_average)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        val inflater = LayoutInflater.from(parent.context)
        return MovieVH(inflater.inflate(R.layout.items_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        val movie = getItem(position)
        holder.render(movie)
        holder.itemView.setOnClickListener { itemClick.onMovieClick(movie) } // Llama al método onMovieClick cuando se selecciona una película
    }
}

object DiffUtilMoviesCallback : DiffUtil.ItemCallback<MoviesInfoUI>() {
    override fun areItemsTheSame(oldItem: MoviesInfoUI, newItem: MoviesInfoUI): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MoviesInfoUI, newItem: MoviesInfoUI): Boolean {
        return oldItem == newItem
    }
}