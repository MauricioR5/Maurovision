package com.cacuango_alexander.maurovision.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cacuango_alexander.maurovision.data.network.entities.movie.ResultsMovies
import com.cacuango_alexander.maurovision.logic.usercases.movie.GetAllPopularsUsercase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListPopularsViewModel : ViewModel() {

    val listItems = MutableLiveData<List<ResultsMovies>>()
    val error = MutableLiveData<String>()

    fun getAllPopulars() {
        viewModelScope.launch(Dispatchers.IO) {
            val userCase = GetAllPopularsUsercase()
            val moviesFlows = userCase.invoke()

            moviesFlows.collect{ movie ->
                movie.onSuccess {
                    listItems.postValue(it.toList())
                }
                movie.onFailure {
                    error.postValue(it.message.toString())
                }
            }

        }
    }
}