package com.cacuango_alexander.maurovision.ui.entities

import com.cacuango_alexander.maurovision.data.network.entities.movie.ProductionCompany
import com.cacuango_alexander.maurovision.data.network.entities.movie.ProductionCountry

data class MoviesInfoUI1 (
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val title: String,
    val popularity: Double,
    val poster_path: String,
    val vote_average: Double,
    val budget: Int,
    val release_date: String,
    val revenue: Int,
    val tagline: String,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>
    )