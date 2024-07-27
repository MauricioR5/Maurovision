package com.cacuango_alexander.maurovision.ui.core

import com.cacuango_alexander.maurovision.data.network.entities.movie.DetailedMovie
import com.cacuango_alexander.maurovision.data.network.entities.movie.ResultsMovies
import com.cacuango_alexander.maurovision.ui.entities.MoviesInfoUI
import com.cacuango_alexander.maurovision.ui.entities.MoviesInfoUI1

fun ResultsMovies.toMoviesInfoUI() = MoviesInfoUI(
    this.id,
    this.original_language,
    this.original_title,
    this.title,
    this.popularity,
    this.poster_path,
    this.vote_average
)

fun DetailedMovie.toMoviesInfoUI1() = MoviesInfoUI1(
    this.id,
    this.original_language,
    this.original_title,
    this.title,
    this.overview,
    this.popularity,
    this.poster_path,
    this.vote_average,
    this.budget,
    this.release_date,
    this.revenue,
    this.tagline,
    this.production_companies,
    this.production_countries
)