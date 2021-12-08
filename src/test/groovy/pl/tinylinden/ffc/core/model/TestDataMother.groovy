package pl.tinylinden.ffc.core.model

import java.time.LocalDate
import java.time.LocalTime

class TestDataMother {

    static ShowingsForDate showingsForDate() {
        new ShowingsForDate(
                date(),
                [showing()]
        )
    }

    static Showing showing() {
        new Showing(
                movieId(),
                startAt(),
                price()
        )
    }

    static MovieId movieId() {
        new MovieId("tt0232500")
    }

    static LocalDate date() {
        LocalDate.of(2021, 12, 7)
    }

    static LocalTime startAt() {
        LocalTime.of(13, 30)
    }

    static Price price() {
        new Price("EUR", 4.95)
    }
}
