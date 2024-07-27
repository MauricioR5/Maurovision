package com.cacuango_alexander.maurovision.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cacuango_alexander.maurovision.logic.usercases.movie.GetDetailedMovieByIdUsercase
import com.cacuango_alexander.maurovision.ui.entities.MoviesInfoUI1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailedMovieViewModel : ViewModel() {

    val movieItem = MutableLiveData<MoviesInfoUI1>()
    val error = MutableLiveData<String>()

    fun loadDetailedMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            GetDetailedMovieByIdUsercase().invoke(movieId).collect { response ->
                response.onSuccess { movie ->
                    movieItem.postValue(movie)
                }
                response.onFailure { throwable ->
                    error.postValue(throwable.message)
                }
            }
        }
    }
}