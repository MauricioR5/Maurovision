package com.cacuango_alexander.maurovision.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cacuango_alexander.maurovision.logic.usercases.movie.GetAllNowPlayingUsercase
import com.cacuango_alexander.maurovision.ui.entities.MoviesInfoUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListNowPlayingViewModel : ViewModel() {

    val listItems = MutableLiveData<List<MoviesInfoUI>>()
    val error = MutableLiveData<String>()

    fun initData() {
        viewModelScope.launch(Dispatchers.IO) {
            GetAllNowPlayingUsercase().invoke().collect { respuesta ->
                respuesta.onSuccess { items ->
                    listItems.postValue(items)
                }
                respuesta.onFailure {
                    error.postValue(it.message.toString()) // Puedes considerar usar un mensaje más descriptivo
                    Log.d("TAG", it.message.toString())
                }
            }


        }
    }
}
