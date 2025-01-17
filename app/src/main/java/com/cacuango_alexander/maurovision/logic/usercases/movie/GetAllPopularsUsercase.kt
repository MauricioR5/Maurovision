package com.cacuango_alexander.maurovision.logic.usercases.movie

import android.util.Log
import com.cacuango_alexander.maurovision.core.Constant
import com.cacuango_alexander.maurovision.data.network.endpoints.movie.PopularsEndpoint
import com.cacuango_alexander.maurovision.data.network.entities.movie.ResultsMovies
import com.cacuango_alexander.maurovision.data.network.repository.RetrofitBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllPopularsUsercase {
    suspend fun invoke(): Flow<Result<List<ResultsMovies>>> = flow {
        var result: Result<List<ResultsMovies>>? = null

        val baseService = RetrofitBase.getRetrofitTmdbConnection()
        val service = baseService.create(PopularsEndpoint::class.java)
        val call = service.getAllPopulars(Constant.API_KEY)
        try {
            if (call.isSuccessful) {
                val a = call.body()!!
                val movie = a.results
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