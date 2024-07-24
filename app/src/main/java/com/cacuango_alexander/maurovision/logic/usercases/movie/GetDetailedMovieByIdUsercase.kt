package com.cacuango_alexander.maurovision.logic.usercases.movie

import android.util.Log
import com.cacuango_alexander.maurovision.core.Constant
import com.cacuango_alexander.maurovision.data.network.endpoints.movie.DetailedMovieEndpoint
import com.cacuango_alexander.maurovision.data.network.entities.movie.DetailedMovie
import com.cacuango_alexander.maurovision.data.network.repository.RetrofitBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetDetailedMovieByIdUsercase {

    suspend fun invoke(movie_id:Int): Flow<Result<DetailedMovie>> = flow {
        var result: Result<DetailedMovie>? = null

        val baseService = RetrofitBase.getRetrofitTmdbConnection()
        val service = baseService.create(DetailedMovieEndpoint::class.java)
        val call = service.getAllNowPlaying(movie_id, Constant.API_KEY)
        try {
            if (call.isSuccessful) {
                val movie = call.body()!!
                result = Result.success(movie)
            } else {
                val msg = "Error en el llamado a la API de Jikan"
                result = Result.failure(Exception(msg))
                Log.d("TAG", msg)
            }
        } catch (ex: Exception) {
            Log.e("TAG", ex.stackTraceToString())
            result = Result.failure(ex)
        }
        emit(result!!)
    }

}