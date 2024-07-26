package com.cacuango_alexander.maurovision.ui.core

import com.cacuango_alexander.maurovision.data.network.entities.movie.ResultsMovies
import com.cacuango_alexander.maurovision.ui.entities.MoviesInfoUI

fun ResultsMovies.toMoviesInfoUI() = MoviesInfoUI(
    this.id,
    this.original_language,
    this.original_title,
    this.title,
    this.popularity,
    this.poster_path,
    this.vote_average
)