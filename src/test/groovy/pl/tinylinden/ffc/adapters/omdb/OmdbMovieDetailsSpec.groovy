package pl.tinylinden.ffc.adapters.omdb

import com.fasterxml.jackson.databind.ObjectMapper
import pl.tinylinden.ffc.config.JacksonConfig
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDate

class OmdbMovieDetailsSpec extends Specification {

    @Shared
    ObjectMapper objectMapper = new JacksonConfig().objectMapper()

    def "should deserialize movie details from true response"() {
        given:
            def response = OmdbDataMother.trueResponse()

        when:
            def actual = objectMapper.readValue(response, OmdbMovieDetails.class)

        then:
            actual.title == "The Fast and the Furious"
            actual.releaseDate == LocalDate.of(2001, 6, 22)
            actual.runtime == "106 min"
            actual.plot ==~ /^Los Angeles police officer Brian O'Conner.+/
            actual.imdbRating == 6.8d
    }

    def "should deserialize movie details from false response"() {
        given:
            def response = OmdbDataMother.falseResponse()

        when:
            def actual = objectMapper.readValue(response, OmdbMovieDetails.class)

        then:
            actual.title == null
            actual.releaseDate == null
            actual.runtime == null
            actual.plot == null
            actual.imdbRating == null
    }
}
