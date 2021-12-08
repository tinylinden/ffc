package pl.tinylinden.ffc.core.ports

import pl.tinylinden.ffc.core.model.ShowingsForDate

interface ShowingsManager {

    fun setOrReplaceShowings(showings: List<ShowingsForDate>)
}