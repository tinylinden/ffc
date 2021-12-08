package pl.tinylinden.ffc.core.model

import java.time.LocalDate

data class MovieDetails(
    val title: String,
    val plot: String,
    val releaseDate: LocalDate,
    val runtime: String,
    val imdbRating: Double
)