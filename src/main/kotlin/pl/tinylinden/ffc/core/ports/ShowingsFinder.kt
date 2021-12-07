package pl.tinylinden.ffc.core.ports

import pl.tinylinden.ffc.core.model.ShowingsForDate
import java.time.LocalDate

interface ShowingsFinder {

    fun findShowings(fromDate: LocalDate, toDate: LocalDate): List<ShowingsForDate>
}