package com.cacuango_alexander.maurovision.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cacuango_alexander.maurovision.R
import com.cacuango_alexander.maurovision.data.network.entities.movie.ResultsMovies
import com.cacuango_alexander.maurovision.databinding.FragmentListUpcomingBinding
import com.cacuango_alexander.maurovision.ui.adapters.MovieAdapter
import com.cacuango_alexander.maurovision.ui.viewmodels.ListUpcomingViewModel


class ListUpcomingFragment : Fragment() {

    private lateinit var binding: FragmentListUpcomingBinding
    private val adapter = MovieAdapter({selectMovie(it)})
    private val viewModel : ListUpcomingViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            FragmentListUpcomingBinding.bind(inflater.inflate(R.layout.fragment_list_upcoming, container, false))
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
        viewModel.listItems.observe(requireActivity()){
            binding.animationView.visibility = View.VISIBLE
            adapter.submitList(it)
            binding.animationView.visibility = View.GONE
        }

        viewModel.error.observe(requireActivity()){
            adapter.listMovies = emptyList()
            adapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView(){
        binding.rvUsers.adapter = adapter
        binding.rvUsers.layoutManager = GridLayoutManager(
            requireActivity(), 1, RecyclerView.HORIZONTAL, false)
    }

    private fun initListeners(){
        binding.swiperv.setOnRefreshListener {
            initRecyclerView()
            binding.swiperv.isRefreshing = false
        }
    }

    private fun selectMovie(movie: ResultsMovies){
        Log.d("TAG", movie.id.toString())
        findNavController()
            .navigate(
                ListUpcomingFragmentDirections
                    .actionListUpcomingFragmentToDetailedMovieFragment(movieId = movie.id)
            )
    }

}