package pl.tinylinden.ffc.core.ports

import pl.tinylinden.ffc.core.model.MovieDetails
import pl.tinylinden.ffc.core.model.MovieId

interface MovieDetailsProvider {

    fun getMovieDetails(movie: MovieId): MovieDetails
}