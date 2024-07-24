package com.cacuango_alexander.maurovision.data.network.endpoints.movie

import com.cacuango_alexander.maurovision.data.network.entities.movie.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UpcomingEndPoint {
    @GET("movie/upcoming")
    suspend fun getAllUpcoming(@Query("api_key") apiKey: String): Response<Movie>
}