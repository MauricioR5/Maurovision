package com.cacuango_alexander.maurovision.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.cacuango_alexander.maurovision.R
import com.cacuango_alexander.maurovision.data.network.entities.movie.ResultsMovies
import com.cacuango_alexander.maurovision.databinding.FragmentListNowPlayingBinding
import com.cacuango_alexander.maurovision.ui.adapters.MovieAdapter
import com.cacuango_alexander.maurovision.ui.viewmodels.ListNowPlayingViewModel


class ListNowPlayingFragment : Fragment() {

    private lateinit var binding: FragmentListNowPlayingBinding
    private val adapter = MovieAdapter({selectMovie(it)})
    private val viewModel : ListNowPlayingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentListNowPlayingBinding.bind(inflater.inflate(R.layout.fragment_list_now_playing, container, false))
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initObservers()
        initRecyclerView()
        viewModel.getAllNowPlaying()

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
        binding.rvUsers.layoutManager = GridLayoutManager(requireActivity(), 2)
    }

    private fun initListeners(){
        binding.swiperv.setOnRefreshListener {
            initRecyclerView()
            binding.swiperv.isRefreshing = false
        }
    }

    private fun selectMovie(movie: ResultsMovies){
        findNavController()
            .navigate(
                ListNowPlayingFragmentDirections
                    .actionListNowPlayingFragmentToDetailedMovieFragment(movieId = movie.id)
            )
    }


}