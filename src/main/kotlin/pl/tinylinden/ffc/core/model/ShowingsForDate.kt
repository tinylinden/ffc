package pl.tinylinden.ffc.core.model

import java.time.LocalDate

data class ShowingsForDate(
    val date: LocalDate,
    val showings: List<Showing>
)
