package pl.tinylinden.ffc.adapters.omdb

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class OmdbMovieDetails(
    @JsonProperty("Title")
    val title: String,

    @JsonProperty("Plot")
    val plot: String,

    @JsonProperty("Released")
    @JsonFormat(pattern = "dd MMM yyyy", locale = "en_US")
    val releaseDate: LocalDate,

    @JsonProperty("Runtime")
    val runtime: String,

    @JsonProperty("imdbRating")
    val imdbRating: Double
)
