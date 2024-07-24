package com.cacuango_alexander.maurovision.data.network.endpoints.movie

import com.cacuango_alexander.maurovision.data.network.entities.movie.DetailedMovie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailedMovieEndpoint {
    @GET("movie/{movie_id}")
    suspend fun getAllNowPlaying(@Path("movie_id") movie_id: Int, @Query("api_key") apiKey: String): Response<DetailedMovie>

}