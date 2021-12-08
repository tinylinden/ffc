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

    static MovieDetails movieDetails() {
        new MovieDetails(
                "The Fast and the Furious",
                "Los Angeles police officer Brian O'Conner must decide where his loyalty really lies when he becomes enamored with the street racing world he has been sent undercover to destroy.",
                LocalDate.of(2001, 6, 22),
                "106 min",
                6.8
        )
    }
}
