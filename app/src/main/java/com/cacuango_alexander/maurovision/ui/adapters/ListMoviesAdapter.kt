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
import com.cacuango_alexander.maurovision.data.network.entities.movie.ResultsMovies
import com.cacuango_alexander.maurovision.databinding.ItemsMovieBinding

class ListMoviesAdapter(
    private var onSelectItem: (ResultsMovies) -> Unit
) : ListAdapter<ResultsMovies, ListMoviesAdapter.MovieVH>(DiffUtilMoviesCallback) {

    class MovieVH(view: View) : RecyclerView.ViewHolder(view) {
        private var binding: ItemsMovieBinding = ItemsMovieBinding.bind(view)

        fun render(item: ResultsMovies, onSelectItem: (ResultsMovies) -> Unit) {
            println("Poster path: ${item.poster_path}") // Imprime el poster_path en la consola
            binding.imgPoster.load(Constant.URL_IMG + item.poster_path)
            binding.circularProgress.maxProgress = 10.0
            binding.circularProgress.setCurrentProgress(item.vote_average)

            binding.imgPoster.setOnClickListener {
                onSelectItem(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        val inflater = LayoutInflater.from(parent.context)
        return MovieVH(inflater.inflate(R.layout.items_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        holder.render(getItem(position), onSelectItem)
    }
}

object DiffUtilMoviesCallback : DiffUtil.ItemCallback<ResultsMovies>() {
    override fun areItemsTheSame(oldItem: ResultsMovies, newItem: ResultsMovies): Boolean {
        return oldItem.id == newItem.id // Usa `id` para una comparación única
    }

    override fun areContentsTheSame(oldItem: ResultsMovies, newItem: ResultsMovies): Boolean {
        return oldItem == newItem
    }


}
