package com.cacuango_alexander.maurovision.data.network.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBase {

    private const val TMDB_URL = ""

    fun getRetrofitTmdbConnection(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(TMDB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}
