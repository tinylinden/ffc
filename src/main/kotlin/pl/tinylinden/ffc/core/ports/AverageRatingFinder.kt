package pl.tinylinden.ffc.core.ports

import pl.tinylinden.ffc.core.model.AverageRating
import pl.tinylinden.ffc.core.model.MovieId

interface AverageRatingFinder {

    fun findAverageRating(movie: MovieId): AverageRating
}