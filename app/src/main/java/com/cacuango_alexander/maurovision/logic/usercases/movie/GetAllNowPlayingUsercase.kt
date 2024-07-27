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
        val baseService = RetrofitBase.getRetrofitTmdbConnection()
        val service = baseService.create(NowPlayingEndpoint::class.java)
        val response = service.getAllNowPlaying(Constant.API_KEY)

        if (response.isSuccessful) {
            val results = response.body()?.results

            if (results != null) {
                val items = results.map {
                    Log.d("GetAllNowPlayingUsercase", "Original poster path: ${it.poster_path}")
                    val movieInfoUI = it.toMoviesInfoUI()
                    Log.d("GetAllNowPlayingUsercase", "Transformed poster path: ${movieInfoUI.poster_path}")
                    movieInfoUI
                }

                Log.d("GetAllNowPlayingUsercase", "Response from API: $results")
                Log.d("GetAllNowPlayingUsercase", "Items to emit: $items")

                emit(Result.success(items))
            } else {
                emit(Result.failure(Exception("No results found")))
            }
        } else {
            emit(Result.failure(Exception("Error: ${response.errorBody()?.string()}")))
        }
    }.catch {
        Log.d("TAG", it.message.toString())
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}
