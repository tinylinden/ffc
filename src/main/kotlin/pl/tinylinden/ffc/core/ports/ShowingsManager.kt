package pl.tinylinden.ffc.core.ports

import pl.tinylinden.ffc.core.model.ShowingsForDate

interface ShowingsManager {

    fun saveShowings(showings: List<ShowingsForDate>)
}