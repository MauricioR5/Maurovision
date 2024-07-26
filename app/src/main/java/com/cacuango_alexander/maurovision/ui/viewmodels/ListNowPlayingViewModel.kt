package com.cacuango_alexander.maurovision.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cacuango_alexander.maurovision.data.network.entities.movie.ResultsMovies
import com.cacuango_alexander.maurovision.logic.usercases.movie.GetAllNowPlayingUsercase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListNowPlayingViewModel : ViewModel() {

    val listItems = MutableLiveData<List<ResultsMovies>>()
    val error = MutableLiveData<String>()

    fun getAllNowPlaying() {
        viewModelScope.launch(Dispatchers.IO) {
            val userCase = GetAllNowPlayingUsercase()
            val movieFlow = userCase.invoke()

            movieFlow.collect { movie ->
                movie.onSuccess {
                    listItems.postValue(it.toList()) // Puedes usar setValue si estás en el hilo principal
                }
                movie.onFailure {
                    error.postValue(it.message.toString()) // Puedes considerar usar un mensaje más descriptivo
                }
            }
        }
    }
}
