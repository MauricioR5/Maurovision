package com.cacuango_alexander.maurovision.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.cacuango_alexander.maurovision.R
import com.cacuango_alexander.maurovision.databinding.FragmentListNowPlayingBinding
import com.cacuango_alexander.maurovision.ui.adapters.ListMoviesAdapter
import com.cacuango_alexander.maurovision.ui.viewmodels.ListNowPlayingViewModel

class ListNowPlayingFragment : Fragment() {

    private lateinit var binding: FragmentListNowPlayingBinding
    private val adapter = ListMoviesAdapter()
    private val viewModel: ListNowPlayingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListNowPlayingBinding.bind(inflater.inflate(R.layout.fragment_list_now_playing, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        binding.animationView.visibility = View.VISIBLE // Muestra la animación antes de cargar los datos
        viewModel.initData()

        // Observar los datos del ViewModel
        viewModel.listItems.observe(viewLifecycleOwner, { movies ->
            // Actualizar el adaptador con las películas
            adapter.submitList(movies)
            binding.animationView.visibility = View.GONE // Oculta la animación después de que los datos se hayan cargado
        })

        viewModel.error.observe(viewLifecycleOwner) {
            // Mostrar el error
            Toast.makeText(requireContext(), "An error occurred", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initRecyclerView() {
        binding.rvUsers.adapter = adapter
        binding.rvUsers.layoutManager = GridLayoutManager(requireActivity(), 2)
    }
}