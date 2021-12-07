package pl.tinylinden.ffc.core.model

import java.time.LocalTime

data class Showing(
    val movie: MovieId,
    val startAt: LocalTime,
    val ticketPrice: Price
)
