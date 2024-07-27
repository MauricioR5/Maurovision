package com.cacuango_alexander.maurovision.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.cacuango_alexander.maurovision.R
import com.cacuango_alexander.maurovision.core.Constant
import com.cacuango_alexander.maurovision.databinding.FragmentDetailedMovieBinding
import com.cacuango_alexander.maurovision.ui.viewmodels.DetailedMovieViewModel
import com.google.android.material.snackbar.Snackbar
import com.cacuango_alexander.maurovision.ui.core.toMoviesInfoUI1

class DetailedMovieFragment : Fragment() {

    private lateinit var binding: FragmentDetailedMovieBinding
    private val args: DetailedMovieFragmentArgs by navArgs()
    private val viewModel: DetailedMovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailedMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        Log.d("TAG_Detail", args.movieId.toString())
        viewModel.loadDetailedMovie(args.movieId)
    }

    private fun initObservers() {
        binding.svDetails.visibility = View.GONE

        viewModel.movieItem.observe(viewLifecycleOwner) { movie ->
            binding.animationView.visibility = View.VISIBLE

            binding.title.text = movie.title
            binding.description.text = movie.overview
            binding.imgMovie.load(Constant.URL_IMG + movie.poster_path)
            binding.budget.text = movie.budget.toString()
            binding.logoCompanie.load(Constant.URL_IMG + movie.production_companies.firstOrNull()?.logo_path ?: "")
            binding.originCountry.text = movie.production_companies.firstOrNull()?.origin_country ?: ""
            binding.popularity.text = movie.popularity.toString()
            binding.releaseDate.text = movie.release_date
            binding.revenue.text = movie.revenue.toString()
            binding.tagline.text = movie.tagline
            binding.productionCompanies.text = movie.production_companies.firstOrNull()?.name ?: ""

            binding.svDetails.visibility = View.VISIBLE
            binding.animationView.visibility = View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Snackbar.make(requireView(), errorMessage.toString(), Snackbar.LENGTH_LONG).show()
        }
    }
}