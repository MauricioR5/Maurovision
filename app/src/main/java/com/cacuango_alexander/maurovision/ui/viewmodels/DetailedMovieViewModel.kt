package com.cacuango_alexander.maurovision.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cacuango_alexander.maurovision.data.network.entities.movie.DetailedMovie
import com.cacuango_alexander.maurovision.logic.usercases.movie.GetDetailedMovieByIdUsercase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailedMovieViewModel : ViewModel(){

    val MovieItem = MutableLiveData<DetailedMovie>()
    val error = MutableLiveData<String>()

    fun loadDetailedMovie(movie_id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val userCase = GetDetailedMovieByIdUsercase()
            val movieFlow = userCase.invoke(movie_id) //

            movieFlow.collect{movie ->
                movie.onSuccess {
                    MovieItem.postValue(it)
                }
                movie.onFailure {
                    error.postValue(it.message.toString())
                }
            }

        }
    }

}