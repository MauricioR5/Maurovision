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


class DetailedMovieFragment : Fragment() {

    private lateinit var binding: FragmentDetailedMovieBinding
    val args: DetailedMovieFragmentArgs by navArgs()
    private val viewModel : DetailedMovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            FragmentDetailedMovieBinding.bind(
                inflater.inflate(R.layout.fragment_detailed_movie, container, false)) // CAMBIAR
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding.txtIdAnime.text = args.idAnime.toString()
        initObservers()
        initListener()
        Log.d("TAG_Detail", args.movieId.toString())
        viewModel.loadDetailedMovie(args.movieId)
    }

    private fun initListener() {
    }


    private fun initObservers(){
        binding.svDetails.visibility = View.GONE
        viewModel.MovieItem.observe(requireActivity()){movie ->

            binding.animationView.visibility = View.VISIBLE

            binding.title.text = movie.title
            binding.description.text = movie.overview
            binding.imgMovie.load(Constant.URL_IMG+movie.backdrop_path)
            binding.budget.text = movie.budget.toString()
            binding.logoCompanie.load(Constant.URL_IMG+movie.production_companies.first().logo_path)
            binding.originCountry.text = movie.production_companies.first().origin_country
            binding.popularity.text = movie.popularity.toString()
            binding.releaseDate.text = movie.release_date
            binding.revenue.text = movie.revenue.toString()
            binding.tagline.text = movie.tagline
            binding.productionCompanies.text = movie.production_companies.first().name

            binding.svDetails.visibility = View.VISIBLE
            binding.animationView.visibility = View.GONE
        }

        viewModel.error.observe(requireActivity()){errorMessage->
            Snackbar
                .make(requireActivity(), binding.cvMovie, errorMessage.toString(), Snackbar.LENGTH_LONG)
                .show()

        }
    }

}