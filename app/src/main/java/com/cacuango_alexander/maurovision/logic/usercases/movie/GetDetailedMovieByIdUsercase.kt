package com.cacuango_alexander.maurovision.logic.usercases.movie

import android.util.Log
import com.cacuango_alexander.maurovision.core.Constant
import com.cacuango_alexander.maurovision.data.network.endpoints.movie.DetailedMovieEndpoint
import com.cacuango_alexander.maurovision.data.network.repository.RetrofitBase
import com.cacuango_alexander.maurovision.ui.core.toMoviesInfoUI1
import com.cacuango_alexander.maurovision.ui.entities.MoviesInfoUI1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetDetailedMovieByIdUsercase {

    suspend operator fun invoke(movie_id:Int): Flow<Result<MoviesInfoUI1>> = flow {
        val baseService = RetrofitBase.getRetrofitTmdbConnection()
        val service = baseService.create(DetailedMovieEndpoint::class.java)
        val response = service.getAllNow(movie_id, Constant.API_KEY)

        if (response.isSuccessful) {
            val movie = response.body()

            if (movie != null) {
                Log.d("GetDetailedMovieByIdUsercase", "Response from API: $movie")
                val movieInfoUI1 = movie.toMoviesInfoUI1()
                Log.d("GetDetailedMovieByIdUsercase", "Transformed movie: $movieInfoUI1")
                emit(Result.success(movieInfoUI1))
            } else {
                emit(Result.failure(Exception("No movie details found")))
            }
        } else {
            emit(Result.failure(Exception("Error: ${response.errorBody()?.string()}")))
        }
    }.catch {
        Log.d("TAG", it.message.toString())
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}