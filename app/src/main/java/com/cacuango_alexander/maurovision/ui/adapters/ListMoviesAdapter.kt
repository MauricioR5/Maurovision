package com.cacuango_alexander.maurovision.ui.adapters

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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ListMoviesAdapter :
    ListAdapter<MoviesInfoUI, ListMoviesAdapter.MovieVH>(DiffUtilMoviesCallback) {

    private val adapterScope = CoroutineScope(Dispatchers.Main + Job())

    class MovieVH(view: View) : RecyclerView.ViewHolder(view) {
        private var binding: ItemsMovieBinding = ItemsMovieBinding.bind(view)

        fun render(item: MoviesInfoUI) {
            binding.imgPoster.load(Constant.URL_IMG + item.poster_path)
            binding.circularProgress.maxProgress = 10.0
            binding.circularProgress.setCurrentProgress(item.vote_average)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        val inflater = LayoutInflater.from(parent.context)
        return MovieVH(
            inflater.inflate(
                R.layout.items_movie,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        val item = getItem(position)
        adapterScope.launch {
            holder.render(item)
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        adapterScope.cancel() // Cancela todas las corutinas cuando el RecyclerView no está en uso
    }
}

object DiffUtilMoviesCallback : DiffUtil.ItemCallback<MoviesInfoUI>() {
    override fun areItemsTheSame(oldItem: MoviesInfoUI, newItem: MoviesInfoUI): Boolean {
        return oldItem.id == newItem.id // Usa `id` para una comparación única
    }

    override fun areContentsTheSame(oldItem: MoviesInfoUI, newItem: MoviesInfoUI): Boolean {
        return oldItem == newItem
    }
}