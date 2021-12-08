package pl.tinylinden.ffc.adapters.omdb

import pl.tinylinden.ffc.config.JacksonConfig
import spock.lang.Specification

import java.time.LocalDate

class OmdbMovieDetailsSpec extends Specification {

    def "should deserialize movie details from json"() {
        given:
            def json = OmdbDataMother.partialResponse()

        when:
            def actual = new JacksonConfig().objectMapper().readValue(json, OmdbMovieDetails.class)

        then:
            actual.title == "The Fast and the Furious"
            actual.releaseDate == LocalDate.of(2001, 6, 22)
            actual.runtime == "106 min"
            actual.plot == "Los Angeles police officer Brian O'Conner..."
            actual.imdbRating == 6.8d
    }
}
