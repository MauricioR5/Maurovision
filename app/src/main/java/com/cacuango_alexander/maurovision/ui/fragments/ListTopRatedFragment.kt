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
import com.cacuango_alexander.maurovision.databinding.FragmentListTopRatedBinding
import com.cacuango_alexander.maurovision.ui.adapters.ListMoviesAdapter
import com.cacuango_alexander.maurovision.ui.viewmodels.ListTopRatedViewModel

class ListTopRatedFragment : Fragment() {

    private lateinit var binding: FragmentListTopRatedBinding
    private val adapter = ListMoviesAdapter() // Cambia aquí al ListMoviesAdapter
    private val viewModel: ListTopRatedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListTopRatedBinding.bind(inflater.inflate(R.layout.fragment_list_top_rated, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initObservers()
        initRecyclerView()
        viewModel.getAllTopRated()
    }

    private fun initObservers() {
        viewModel.listItems.observe(viewLifecycleOwner) {
            binding.animationView.visibility = View.VISIBLE
            //     adapter.submitList(it)
            binding.animationView.visibility = View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) {
            adapter.submitList(emptyList())
        }
    }

    private fun initRecyclerView() {
        binding.rvUsers.adapter = adapter
        binding.rvUsers.layoutManager = LinearLayoutManager(requireActivity())
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
                ListTopRatedFragmentDirections
                    .actionListTopRatedFragmentToDetailedMovieFragment(movieId = movie.id)
            )
    }
}
