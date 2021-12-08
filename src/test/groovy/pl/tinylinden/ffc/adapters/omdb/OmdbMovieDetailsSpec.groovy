package pl.tinylinden.ffc.adapters.omdb

import pl.tinylinden.ffc.config.JacksonConfig
import spock.lang.Specification

import java.time.LocalDate

class OmdbMovieDetailsSpec extends Specification {

    def "should deserialize movie details from json"() {
        given:
            def json = """
                                |{  
                                |   "Title":"The Fast and the Furious",
                                |   "Released":"22 Jun 2001",
                                |   "Runtime":"106 min",
                                |   "Plot":"Los Angeles police officer Brian O'Conner...",
                                |   "imdbRating":"6.8"
                                |}""".stripMargin()

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
