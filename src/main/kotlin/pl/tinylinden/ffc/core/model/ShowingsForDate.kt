package pl.tinylinden.ffc.core.model

import java.time.LocalDate

data class ShowingsForDate(
    val date: LocalDate,
    val showings: List<Showing>
) {

    fun onlyFor(movie: MovieId): ShowingsForDate =
        copy(date = date, showings = showings.filter { it.movie == movie })
}
