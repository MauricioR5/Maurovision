package com.cacuango_alexander.maurovision.logic.usercases.movie

import android.util.Log
import com.cacuango_alexander.maurovision.core.Constant
import com.cacuango_alexander.maurovision.data.network.endpoints.movie.NowPlayingEndpoint
import com.cacuango_alexander.maurovision.data.network.entities.movie.ResultsMovies
import com.cacuango_alexander.maurovision.data.network.repository.RetrofitBase
import com.cacuango_alexander.maurovision.ui.core.toMoviesInfoUI
import com.cacuango_alexander.maurovision.ui.entities.MoviesInfoUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetAllNowPlayingUsercase {

    suspend operator fun invoke() = flow {
        var result: Result<List<ResultsMovies>>? = null

        val baseService = RetrofitBase.getRetrofitTmdbConnection()
        val service = baseService.create(NowPlayingEndpoint::class.java)
        val response = service.getAllNowPlaying(Constant.API_KEY)

        if (response.isSuccessful) {
            val a = response.body()?.results

            val items = ArrayList<MoviesInfoUI>()
            a?.forEach {
                items.add(it.toMoviesInfoUI())
            }

            Log.d("GetAllNowPlayingUsercase", "Response from API: $a")
            Log.d("GetAllNowPlayingUsercase", "Items to emit: $items")

            emit(Result.success(items.toList()))
        } else {
            emit(Result.failure(Exception("Error: ${response.errorBody()?.string()}")))
        }
    }.catch {
        Log.d("TAG", it.message.toString())
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}