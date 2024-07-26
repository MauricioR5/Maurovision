package com.cacuango_alexander.maurovision.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cacuango_alexander.maurovision.R
import com.cacuango_alexander.maurovision.data.network.entities.movie.ResultsMovies
import com.cacuango_alexander.maurovision.databinding.FragmentListUpcomingBinding
import com.cacuango_alexander.maurovision.ui.adapters.ListMoviesAdapter
import com.cacuango_alexander.maurovision.ui.core.toMoviesInfoUI
import com.cacuango_alexander.maurovision.ui.viewmodels.ListUpcomingViewModel

class ListUpcomingFragment : Fragment() {

    private lateinit var binding: FragmentListUpcomingBinding
    private val adapter = ListMoviesAdapter () // Cambia aquí al ListMoviesAdapter
    private val viewModel: ListUpcomingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListUpcomingBinding.bind(inflater.inflate(R.layout.fragment_list_upcoming, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initObservers()
        initRecyclerView()
        viewModel.getAllUpcoming()
    }

  private fun initObservers() {
    viewModel.listItems.observe(viewLifecycleOwner) {
        binding.animationView.visibility = View.VISIBLE
        val moviesInfoUIList = it.map { resultMovie ->
            resultMovie.toMoviesInfoUI()
        }
        (binding.rvUsers.adapter as ListMoviesAdapter).submitList(moviesInfoUIList)
        binding.animationView.visibility = View.GONE
    }

    viewModel.error.observe(viewLifecycleOwner) {
        adapter.submitList(emptyList())
    }
}

    private fun initRecyclerView() {
        binding.rvUsers.adapter = adapter
        binding.rvUsers.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
    }

    private fun initListeners() {
        binding.swiperv.setOnRefreshListener {
            binding.swiperv.isRefreshing = false
        }
    }

    private fun selectMovie(movie: ResultsMovies) {
        Log.d("TAG", movie.id.toString())
        findNavController()
            .navigate(
                ListUpcomingFragmentDirections
                    .actionListUpcomingFragmentToDetailedMovieFragment(movieId = movie.id)
            )
    }
}
