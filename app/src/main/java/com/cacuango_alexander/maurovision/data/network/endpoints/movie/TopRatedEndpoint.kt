package com.cacuango_alexander.maurovision.data.network.endpoints.movie

import com.cacuango_alexander.maurovision.data.network.entities.movie.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TopRatedEndpoint {
    @GET("movie/top_rated?language=en-US&page=1")
    suspend fun getAllTopRated(@Query("api_key") apiKey: String): Response<Movie>
}