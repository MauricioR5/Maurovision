package com.cacuango_alexander.maurovision.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cacuango_alexander.maurovision.R
import com.cacuango_alexander.maurovision.ui.entities.MoviesInfoUI
import com.cacuango_alexander.maurovision.databinding.FragmentListPopularsBinding
import com.cacuango_alexander.maurovision.ui.adapters.ListMoviesAdapter
import com.cacuango_alexander.maurovision.ui.adapters.OnMovieClickListener
import com.cacuango_alexander.maurovision.ui.viewmodels.ListPopularsViewModel

class ListPopularsFragment : Fragment(), OnMovieClickListener {

    private lateinit var binding: FragmentListPopularsBinding
    private lateinit var adapter: ListMoviesAdapter
    private val viewModel: ListPopularsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListPopularsBinding.bind(inflater.inflate(R.layout.fragment_list_populars, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListMoviesAdapter(this) // Pasamos this al adaptador

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvUsers)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        // Aquí continúa el resto de tu código...

        // Inicializa el RecyclerView
        initRecyclerView()
        initListeners()

        // Muestra la animación antes de cargar los datos
        binding.animationView.visibility = View.VISIBLE
        viewModel.initData()

        // Observa los datos del ViewModel
        viewModel.listItems.observe(viewLifecycleOwner, { movies ->
            // Actualiza el adaptador con las películas
            adapter.submitList(movies)
            // Oculta la animación después de que los datos se hayan cargado
            binding.animationView.visibility = View.GONE
        })

        // Observa los errores del ViewModel
        viewModel.error.observe(viewLifecycleOwner) {
            // Muestra el error
            Toast.makeText(requireContext(), "An error occurred", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initRecyclerView() {
        binding.rvUsers.adapter = adapter
       // binding.rvUsers.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
    }

    private fun initListeners() {
        binding.swiperv.setOnRefreshListener {
            initRecyclerView()
            binding.swiperv.isRefreshing = false
        }
    }

    override fun onMovieClick(movie: MoviesInfoUI) { // Implementamos el método onMovieClick
        Log.d("TAG", movie.id.toString())
        findNavController()
            .navigate(
                ListPopularsFragmentDirections
                    .actionListPopularsFragmentToDetailedMovieFragment(movieId = movie.id)
            )
    }
}